package com.ahmedbenfadhel.githubdashboard.di.component

import com.ahmedbenfadhel.githubdashboard.data.remote.GitHubService
import com.ahmedbenfadhel.githubdashboard.di.module.NetworkModule
import dagger.Component

@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {
    val gitHubService: GitHubService
}