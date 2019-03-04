package com.ahmedbenfadhel.githubdashboard.di.module

import dagger.Provides
import android.app.Activity
import android.content.Context
import com.ahmedbenfadhel.githubdashboard.di.interfaces.GithubApplicationScope
import dagger.Module
import javax.inject.Named


@Module
class ActivityModule(context: Activity) {

    private val context: Context

    init {
        this.context = context
    }

    @Named("activity_context")
    @GithubApplicationScope
    @Provides
    fun context(): Context {
        return context
    }
}