package com.example.gitapp.di


import com.example.gitapp.data.api.GithubApi
import com.example.gitapp.data.repository.UserRepositoryImpl
import com.example.gitapp.domain.repository.UserRepository
import com.example.gitapp.util.Constants
import com.ramcosta.composedestinations.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl: HttpUrl = original.url

                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("key", "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")
                        .build()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .url(url)
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
        }.build()
    }
    @Provides
    @Singleton
    fun provideGitApi(okHttpClient: OkHttpClient): GithubApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GithubApi::class.java)
    }
    @Provides
    @Singleton
    fun provideGamesRepository(githubApi: GithubApi): UserRepository {
        return UserRepositoryImpl(githubApi)
    }
}