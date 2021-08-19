package me.arkty.flickrer.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class FlickrMethodInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val flickrMethod = originalRequest
            .tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(FlickMethod::class.java)

        return if (flickrMethod != null) {
            val requestBuilder = originalRequest.newBuilder()
            requestBuilder.url(
                chain.request().url.newBuilder().apply {
                    addQueryParameter(PARAM_METHOD, flickrMethod.method)
                }.build()
            ).build()
        } else {
            originalRequest
        }.let {
            chain.proceed(it)
        }
    }

    companion object {
        private const val PARAM_METHOD = "method"
    }
}