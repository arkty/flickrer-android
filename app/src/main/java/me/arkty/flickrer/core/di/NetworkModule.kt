package me.arkty.flickrer.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.arkty.flickrer.BuildConfig
import me.arkty.flickrer.data.remote.FlickrApi
import me.arkty.flickrer.data.remote.interceptors.FlickrApiKeyInterceptor
import me.arkty.flickrer.data.remote.interceptors.FlickrJsonFormatInterceptor
import me.arkty.flickrer.data.remote.interceptors.FlickrMethodInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideClient(
        apiKeyInterceptor: FlickrApiKeyInterceptor,
        jsonFormatInterceptor: FlickrJsonFormatInterceptor,
        methodInterceptor: FlickrMethodInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(apiKeyInterceptor)
            .addNetworkInterceptor(jsonFormatInterceptor)
            .addNetworkInterceptor(methodInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): FlickrApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FLICKR_API_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build().create(FlickrApi::class.java)
    }
}