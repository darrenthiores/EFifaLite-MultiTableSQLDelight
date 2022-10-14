package com.darrenthiores.core.data.local.player

import com.darrenthiores.core.PlayerDatabase
import efifalitecore.playerdb.PlayerRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRemoteKeysDataSourceImpl(
    db: PlayerDatabase
): PlayerRemoteKeysDataSource {
    private val remoteKeysQueries = db.playerRemoteKeysQueries

    override suspend fun getRemoteKeys(id: Long): PlayerRemoteKeys? =
        withContext(Dispatchers.Default) {
            remoteKeysQueries.getRemoteKeys(id)
                .executeAsOneOrNull()
        }

    override fun addAllRemoteKeys(remoteKeys: List<PlayerRemoteKeys>) =
        remoteKeysQueries.transaction {
            remoteKeys.forEach { remoteKey ->
                remoteKeysQueries.insertRemoteKeys(
                    id = null,
                    prevPage = remoteKey.prevPage,
                    nextPage = remoteKey.nextPage
                )
            }
        }

    override fun deleteAllRemoteKeys() = remoteKeysQueries.deleteAllRemoteKeys()
}