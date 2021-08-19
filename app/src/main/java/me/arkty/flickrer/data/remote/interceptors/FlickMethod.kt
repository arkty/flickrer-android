package me.arkty.flickrer.data.remote.interceptors

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FlickMethod(
    val method: String = ""
)
