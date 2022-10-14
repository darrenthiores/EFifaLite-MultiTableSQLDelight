package com.darrenthiores.efifalite

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.darrenthiores.core.di.databaseModule
import com.darrenthiores.core.di.networkModule
import com.darrenthiores.core.di.repositoryModule
import com.darrenthiores.efifalite.di.useCaseModule
import com.darrenthiores.efifalite.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@ExperimentalPagingApi
open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {

            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )

        }

    }
}