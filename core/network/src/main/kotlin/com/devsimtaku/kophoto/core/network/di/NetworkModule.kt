package com.devsimtaku.kophoto.core.network.di

import com.devsimtaku.kophoto.core.network.BuildConfig
import com.devsimtaku.kophoto.core.network.PhotoDataSource
import com.devsimtaku.kophoto.core.network.retrofit.KoPhotoApi
import com.devsimtaku.kophoto.core.network.retrofit.RetrofitPhotoDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("serviceKey")
    fun provideServiceKey(): String {
        return BuildConfig.SERVICE_KEY
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("serviceKey") serviceKey: String
    ): OkHttpClient {
        val serviceKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val url = originalUrl.newBuilder()
                .addQueryParameter("serviceKey", serviceKey)
                .build()

            val requestBuilder = originalRequest.newBuilder()
                .url(url)

            chain.proceed(requestBuilder.build())
        }

        return OkHttpClient.Builder()
            .addInterceptor(serviceKeyInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoAwardApi(retrofit: Retrofit): KoPhotoApi {
        return retrofit.create(KoPhotoApi::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoDataSource(
        photoAwardApi: KoPhotoApi
    ): PhotoDataSource {
        return RetrofitPhotoDataSource(photoAwardApi)
    }
}
