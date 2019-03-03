package com.ahmedbenfadhel.githubdashboard.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedbenfadhel.githubdashboard.data.local.dao.ProjectDao
import com.ahmedbenfadhel.githubdashboard.data.local.dao.UserDao
import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity
import com.ahmedbenfadhel.githubdashboard.data.remote.GitHubService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class GithubRepository(private val githubService: GitHubService, private val userDao: UserDao, val projectDao: ProjectDao) {
    val data = MutableLiveData<List<ProjectEntity>>()


    fun fetchUserByName(username: String): LiveData<List<ProjectEntity>> {
        doAsync {
            val user = userDao.getUserByName(username)
            val userExists = user != null
            if (!userExists) {
                githubService.getProjectList(username).enqueue(object : Callback<List<ProjectEntity>> {
                    override fun onResponse(call: Call<List<ProjectEntity>>, response: Response<List<ProjectEntity>>) {
                        val result = response.body()
                        if (result != null) {
                            data.value = response.body()
                            saveData(username, result)
                        }
                    }

                    override fun onFailure(call: Call<List<ProjectEntity>>, t: Throwable) {
                        // TODO Error handling
                        data.value = null
                    }
                })
            } else {

                val projectListFromDb = getProjectFromDb(user!!.id)
                uiThread {
                    data.value = projectListFromDb
                }
            }
        }
        return data
    }

    private fun getProjectFromDb(userId: Long): List<ProjectEntity> {
        return projectDao.getProjectsByUser(userId)
    }

    fun saveData(username: String, projectList: List<ProjectEntity>) {
        doAsync {
            val userToSave = projectList[0].owner

            for( project in projectList)
            {
                project.ownerId = userToSave.id
            }

            /**
             * Most of the time we have a user with empty name
             */
            if (userToSave.name.isNullOrEmpty() || userToSave.name.isNullOrBlank()) {
                userToSave.name = username
            }
            userDao.insertUser(user = userToSave)
            projectDao.insert(projectList)
        }
    }
}