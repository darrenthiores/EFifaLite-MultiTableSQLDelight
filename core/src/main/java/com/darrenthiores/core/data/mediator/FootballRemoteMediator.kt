package com.darrenthiores.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.remote.source.RemoteDataSource
import com.darrenthiores.core.utils.DataMapper
import efifalitecore.playerdb.PlayerEntity
import efifalitecore.playerdb.PlayerRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber

@ExperimentalPagingApi
class FootballRemoteMediator(
    private val league: Int,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): RemoteMediator<Long, PlayerEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Long, PlayerEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            Timber.d("$currentPage")

            val response = remoteDataSource.getPlayers(league, currentPage)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1L) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            localDataSource.getPlayerDb().transaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.clearAll()
                    localDataSource.deleteAllRemoteKeys()
                }
                val keys = response.map {
                    PlayerRemoteKeys(
                        id = 0,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                localDataSource.addAllRemoteKeys(remoteKeys = keys)
                localDataSource.insert(response.map { playerResponse ->  DataMapper.mapResponsesToEntities(playerResponse) })
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Long, PlayerEntity>
    ): PlayerRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { id ->
                localDataSource.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Long, PlayerEntity>
    ): PlayerRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { player ->
                localDataSource.getRemoteKeys(id = player.remoteId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Long, PlayerEntity>
    ): PlayerRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { player ->
                localDataSource.getRemoteKeys(id = player.remoteId)
            }
    }

}