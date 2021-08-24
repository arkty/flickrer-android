package me.arkty.flickrer.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import me.arkty.flickrer.BuildConfig
import me.arkty.flickrer.data.remote.FlickrApi
import me.arkty.flickrer.data.remote.interceptors.FlickrApiKeyInterceptor
import me.arkty.flickrer.data.remote.interceptors.FlickrJsonFormatInterceptor
import me.arkty.flickrer.data.remote.interceptors.FlickrMethodInterceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val json = Json(Json) {
        ignoreUnknownKeys = true
    }

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

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideConverterFactory(): Converter.Factory {
        return json.asConverterFactory(
            "application/json".toMediaType()
        )
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient, converterFactory: Converter.Factory): FlickrApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FLICKR_API_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build().create(FlickrApi::class.java)
    }
}