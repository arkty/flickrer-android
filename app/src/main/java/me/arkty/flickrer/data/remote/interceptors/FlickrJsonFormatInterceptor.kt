package me.arkty.flickrer.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class FlickrJsonFormatInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().url(
            chain.request().url.newBuilder()
                .addQueryParameter(PARAM_FORMAT, VALUE_FORMAT_JSON)
                .addQueryParameter(PARAM_NO_CALLBACK, VALUE_NO_CALLBACK)
                .build()
        ).let {
            chain.proceed(it.build())
        }
    }

    companion object {
        private const val PARAM_FORMAT = "format"
        private const val VALUE_FORMAT_JSON = "json"
        private const val PARAM_NO_CALLBACK = "nojsoncallback"
        private const val VALUE_NO_CALLBACK = "true"
    }
}