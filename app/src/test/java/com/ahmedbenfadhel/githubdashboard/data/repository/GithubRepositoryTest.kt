package com.ahmedbenfadhel.githubdashboard.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedbenfadhel.githubdashboard.data.local.GithubDatabase
import com.ahmedbenfadhel.githubdashboard.data.local.dao.ProjectDao
import com.ahmedbenfadhel.githubdashboard.data.local.dao.UserDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GithubRepositoryTest {
    private lateinit var db: GithubDatabase
    private lateinit var uDAO: UserDao
    private lateinit var pDAO: ProjectDao

    @Before
    fun onCreateDB() {

        val context = getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java).build()
        uDAO = db.userDao()
        pDAO = db.projectDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }
}
