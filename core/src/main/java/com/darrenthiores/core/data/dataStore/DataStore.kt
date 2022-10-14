package com.darrenthiores.core.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "ASSETS"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStore(
    private val context: Context
) {

    private val _coin = intPreferencesKey(COIN_KEY)
    val coin: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[_coin] ?: 0
        }

    suspend fun updateCoin(coin: Int) {
        context.dataStore.edit { settings ->
            val currentCoin = settings[_coin] ?: 1000
            settings[_coin] = currentCoin + coin
        }
    }

    private val _login = booleanPreferencesKey(LOGIN_KEY)
    val login: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[_login] ?: true
        }

    suspend fun updateLogin(login: Boolean) {
        context.dataStore.edit { settings ->
            settings[_login] = login
        }
    }

    private val _formation = intPreferencesKey(FORMATION_KEY)
    val formation: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[_formation] ?: 433
        }

    suspend fun updateFormation(formation: Int) {
        context.dataStore.edit { settings ->
            settings[_formation] = formation
        }
    }

    companion object{
        private const val COIN_KEY = "COIN_KEY"
        private const val LOGIN_KEY = "LOGIN_KEY"
        private const val FORMATION_KEY = "FORMATION_KEY"
    }

}