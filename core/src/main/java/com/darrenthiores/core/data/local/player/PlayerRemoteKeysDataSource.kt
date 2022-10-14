package com.darrenthiores.core.data.local.player

import efifalitecore.playerdb.PlayerRemoteKeys

interface PlayerRemoteKeysDataSource {
    suspend fun getRemoteKeys(id: Long): PlayerRemoteKeys?

    fun addAllRemoteKeys(remoteKeys: List<PlayerRemoteKeys>)

    fun deleteAllRemoteKeys()
}