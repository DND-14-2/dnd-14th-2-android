package com.smtm.pickle.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.smtm.pickle.data.BuildConfig
import com.smtm.pickle.data.source.remote.api.AuthService
import com.smtm.pickle.data.source.remote.api.LedgerApi
import com.smtm.pickle.data.source.remote.api.MateApi
import com.smtm.pickle.data.source.remote.api.RefreshTokenApi
import com.smtm.pickle.data.source.remote.api.UserApi
import com.smtm.pickle.data.source.remote.auth.TokenAuthenticator
import com.smtm.pickle.domain.provider.TokenProvider
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
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true    // 서버 응답에 모르는 필드 무시
        coerceInputValues = true    // null -> 기본값 사용
        prettyPrint = BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            redactHeader("Authorization")
            redactHeader("Cookie")

            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): Interceptor {
        // 로그인 요청이 아니면 토큰 추가
        return Interceptor { chain ->
            val originalRequest = chain.request()

            if (originalRequest.url.encodedPath.contains("oauth/login")) {
                return@Interceptor chain.proceed(originalRequest)
            }

            val accessToken = tokenProvider.getCachedToken()?.access

            val newRequest = accessToken?.let {
                originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
            } ?: originalRequest

            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        tokenAuthenticator: TokenAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @Named("RefreshOkHttp")
    fun provideRefreshOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("RefreshRetrofit")
    fun provideRefreshRetrofit(
        json: Json,
        @Named("RefreshOkHttp") okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideRefreshApi(
        @Named("RefreshRetrofit") retrofit: Retrofit
    ): RefreshTokenApi =
        retrofit.create(RefreshTokenApi::class.java)

    @Provides
    @Singleton
    fun provideLedgerApi(retrofit: Retrofit): LedgerApi {
        return retrofit.create(LedgerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMateApi(retrofit: Retrofit): MateApi {
        return retrofit.create(MateApi::class.java)
    }
}
