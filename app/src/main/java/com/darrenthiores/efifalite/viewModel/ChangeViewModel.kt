package com.darrenthiores.efifalite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darrenthiores.core.domain.FootballUseCase
import com.darrenthiores.core.model.presenter.Starting
import com.darrenthiores.core.model.presenter.UserPlayer
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChangeViewModel(
    private val footballUseCase: FootballUseCase
): ViewModel() {

    fun checkStartingExist(playerId: Int): Boolean = runBlocking {
        footballUseCase.checkStartingExist(playerId)
    }

    fun getStartingById(id: Int): Flow<Starting> = runBlocking {
        footballUseCase.getStartingById(id).map { DataMapper.mapStartingToPresenter(it) }
    }

    fun getPlayersByPosition(position: String): Flow<List<UserPlayer>> =
        runBlocking {
            footballUseCase.getPlayersByPosition(position).map {
                DataMapper.mapUserPlayerToPresenter(it)
            }
        }

    fun onPlayerChange(starting: Starting, userPlayer: UserPlayer, place: Int) = viewModelScope.launch {
        footballUseCase.updateStarting(
            DataMapper.mapUserPlayerPresenterToDomain(userPlayer),
            DataMapper.mapStartingPresenterToDomain(starting),
            place
        )
    }
}