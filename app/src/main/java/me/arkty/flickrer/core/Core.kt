package me.arkty.flickrer.core

import android.app.Application
import androidx.databinding.DataBindingUtil
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import me.arkty.flickrer.BuildConfig
import me.arkty.flickrer.core.android.di.BindingComponentBuilder
import me.arkty.flickrer.utils.extensions.bindings.BindingEntryPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class Core : Application() {

    @Inject
    lateinit var bindingComponentProvider: Provider<BindingComponentBuilder>

    override fun onCreate() {
        super.onCreate()
        initBindingComponent()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initBindingComponent() {
        val component = bindingComponentProvider.get().build()
        val entry = EntryPoints.get(
            component, BindingEntryPoint::class.java
        )
        DataBindingUtil.setDefaultComponent(entry)
    }
}