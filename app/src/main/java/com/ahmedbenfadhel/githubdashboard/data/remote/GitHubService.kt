package com.ahmedbenfadhel.githubdashboard.data.remote

import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity
import com.ahmedbenfadhel.githubdashboard.data.local.entity.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{login}/repos")
    fun getProjectList(@Path("login") user: String): Call<List<ProjectEntity>>

}