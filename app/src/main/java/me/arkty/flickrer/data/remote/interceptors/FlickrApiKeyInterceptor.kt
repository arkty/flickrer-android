package me.arkty.flickrer.data.remote.interceptors

import me.arkty.flickrer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class FlickrApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().url(
            chain.request().url.newBuilder().apply {
                addQueryParameter(PARAM_API_KEY, BuildConfig.FLICKR_API_KEY)
            }.build()
        ).let {
            chain.proceed(it.build())
        }
    }
    companion object{
        private const val PARAM_API_KEY = "api_key"
    }
}