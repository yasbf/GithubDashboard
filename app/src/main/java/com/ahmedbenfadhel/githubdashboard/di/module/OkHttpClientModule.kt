package com.ahmedbenfadhel.githubdashboard.di.module

import android.content.Context
import dagger.Module
import okhttp3.logging.HttpLoggingInterceptor
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.File


@Module(includes=arrayOf(ContextModule::class))
class OkHttpClientModule {

    @Provides
    fun okHttpClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
    }

    @Provides
    fun file(context: Context): File {
        val file = File(context.getCacheDir(), "HttpCache")
        file.mkdirs()
        return file
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}