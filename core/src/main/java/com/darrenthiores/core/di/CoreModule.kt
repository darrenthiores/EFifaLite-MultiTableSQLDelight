package com.darrenthiores.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.sqlite.db.SupportSQLiteDatabase
import com.darrenthiores.core.BuildConfig
import com.darrenthiores.core.PlayerDatabase
import com.darrenthiores.core.data.dataStore.DataStore
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.local.player.PlayerDataSource
import com.darrenthiores.core.data.local.player.PlayerDataSourceImpl
import com.darrenthiores.core.data.local.player.PlayerRemoteKeysDataSource
import com.darrenthiores.core.data.local.player.PlayerRemoteKeysDataSourceImpl
import com.darrenthiores.core.data.local.starting.StartingDataSource
import com.darrenthiores.core.data.local.starting.StartingDataSourceImpl
import com.darrenthiores.core.data.local.userPlayer.UserPlayerDataSource
import com.darrenthiores.core.data.local.userPlayer.UserPlayerDataSourceImpl
import com.darrenthiores.core.data.remote.ktor.FootballService
import com.darrenthiores.core.data.remote.ktor.FootballServiceImpl
import com.darrenthiores.core.data.remote.source.RemoteDataSource
import com.darrenthiores.core.data.repository.FootballRepository
import com.darrenthiores.core.data.repository.IFootballRepository
import com.darrenthiores.core.utils.PrepopulateHelper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<SqlDriver> {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSWORD.toCharArray())
        val factory = SupportFactory(passphrase)

        AndroidSqliteDriver(
            schema = PlayerDatabase.Schema,
            context = androidApplication(),
            name = "player.db",
            factory = factory,
            callback = object : AndroidSqliteDriver.Callback(PlayerDatabase.Schema) {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    PrepopulateHelper.fillWithStartingData(db)
                    PrepopulateHelper.fillWithUserPlayer(db)
                }
            }
        )
    }

    single<PlayerDataSource> { PlayerDataSourceImpl(PlayerDatabase(get())) }
    single<PlayerRemoteKeysDataSource> { PlayerRemoteKeysDataSourceImpl(PlayerDatabase(get())) }
    single<UserPlayerDataSource> { UserPlayerDataSourceImpl(PlayerDatabase(get())) }
    single<StartingDataSource> { StartingDataSourceImpl(PlayerDatabase(get())) }

}

val networkModule = module {
    single {
        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(HttpTimeout) { // Timeout
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }
    }
    single<FootballService> { FootballServiceImpl(get()) }
}

@ExperimentalPagingApi
val repositoryModule = module {

    single { LocalDataSource(PlayerDatabase(get()), get(), get(), get(), get()) }
    single { RemoteDataSource(get()) }
    single { DataStore(androidContext()) }

    single<IFootballRepository> { FootballRepository(get(), get(), get()) }

}