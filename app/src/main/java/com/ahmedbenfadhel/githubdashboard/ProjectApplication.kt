package com.ahmedbenfadhel.githubdashboard

import android.app.Application
import android.content.Context

class ProjectApplication : Application() {

    val Context.myApp: ProjectApplication
        get() = applicationContext as ProjectApplication
}
