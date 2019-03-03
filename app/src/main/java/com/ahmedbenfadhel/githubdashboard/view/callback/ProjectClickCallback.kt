package com.ahmedbenfadhel.githubdashboard.view.callback

import com.ahmedbenfadhel.githubdashboard.data.remote.model.Project

interface ProjectClickCallback {
    fun onClick(project: Project)
}