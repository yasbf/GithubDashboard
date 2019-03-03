package com.ahmedbenfadhel.githubdashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedbenfadhel.githubdashboard.data.remote.model.Project

class ProjectViewModel : ViewModel() {
    /**
     * Expose the LiveData Project so the UI can observe it.
     */
    val selectedProject: MutableLiveData<Project> = MutableLiveData()


    fun setSelectedProject(project: Project?) {
        selectedProject.value = project
    }

}