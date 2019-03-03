package com.ahmedbenfadhel.githubdashboard.data.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class UserConverter {
    @TypeConverter
    fun fromString(value: String): UserEntity {
        val userType = object : TypeToken<UserEntity>() {

        }.type
        return Gson().fromJson(value, userType)
    }

    @TypeConverter
    fun fromUser(user: UserEntity): String {
        val gson = Gson()
        val json = gson.toJson(user)
        return json
    }
}