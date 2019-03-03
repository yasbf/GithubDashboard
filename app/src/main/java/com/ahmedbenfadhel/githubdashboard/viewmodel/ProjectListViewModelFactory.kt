package com.ahmedbenfadhel.githubdashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedbenfadhel.githubdashboard.data.repository.GithubRepository

class ProjectListViewModelFactory(private val githubRepository: GithubRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProjectListViewModel::class.java)) {
            ProjectListViewModel(githubRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}