package com.ahmedbenfadhel.githubdashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity
import com.ahmedbenfadhel.githubdashboard.data.repository.GithubRepository

class ProjectListViewModel(projectRepository: GithubRepository) : ViewModel() {
    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    val githubRepository: GithubRepository = projectRepository

    fun search(query: String): List<ProjectEntity>? {
        return githubRepository.fetchUserByName(query).value
    }

}
