package com.ahmedbenfadhel.githubdashboard.data.local.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "project",
    indices = [Index("ownerId")],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = CASCADE)])
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "ownerId")
    var ownerId: Long,
    @TypeConverters(UserConverter::class)
    val owner: UserEntity,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "git_url")
    val git_url: String?,
   @ColumnInfo(name = "stargazers_count")
    val stargazers_count: String?

)