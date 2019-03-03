package com.ahmedbenfadhel.githubdashboard.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "html_url")
    var html_url: String? = null
)