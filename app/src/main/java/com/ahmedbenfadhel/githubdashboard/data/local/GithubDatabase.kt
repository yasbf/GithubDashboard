package com.ahmedbenfadhel.githubdashboard.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmedbenfadhel.githubdashboard.data.local.dao.ProjectDao
import com.ahmedbenfadhel.githubdashboard.data.local.dao.UserDao
import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity
import com.ahmedbenfadhel.githubdashboard.data.local.entity.UserConverter
import com.ahmedbenfadhel.githubdashboard.data.local.entity.UserEntity

@TypeConverters(UserConverter::class)
@Database(entities = [UserEntity::class, ProjectEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: GithubDatabase? = null

        fun getDataBase(context: Context): GithubDatabase? {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, GithubDatabase::class.java, "githubDatabase")
                            //.fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

    }
}
