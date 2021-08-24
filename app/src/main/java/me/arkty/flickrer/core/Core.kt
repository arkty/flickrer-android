package me.arkty.flickrer.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.arkty.flickrer.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class Core : Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}