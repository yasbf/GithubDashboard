package com.ahmedbenfadhel.githubdashboard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ahmedbenfadhel.githubdashboard.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE name == :name")
    fun getUserByName(name: String): UserEntity?

    @Query("SELECT * FROM user WHERE id == :id")
    fun getUserById(id: Long): UserEntity

    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserEntity?): Long
}