package com.ahmedbenfadhel.githubdashboard.data.local.dao

import androidx.room.*
import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity

@Dao
interface ProjectDao {


    @Query("SELECT * FROM project WHERE ownerId == :id")
    fun getProjectsByUser(id: Long): List<ProjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: List<ProjectEntity>)
}