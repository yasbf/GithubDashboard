package com.ahmedbenfadhel.githubdashboard.di.module

import com.ahmedbenfadhel.githubdashboard.BuildConfig
import com.ahmedbenfadhel.githubdashboard.data.remote.GitHubService
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import dagger.Provides
import com.google.gson.GsonBuilder
import dagger.Module
import retrofit2.Retrofit
import okhttp3.OkHttpClient


@Module(includes=arrayOf(OkHttpClientModule::class))
class NetworkModule {

    @Provides
    fun githubService(retrofit: Retrofit): GitHubService {
        return retrofit.create(GitHubService::class.java)
    }

    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory, gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}