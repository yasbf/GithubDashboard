package com.ahmedbenfadhel.githubdashboard.data.remote.model

import android.os.Parcel
import android.os.Parcelable

data class Project(
    var name: String? = null,
    var owner: User? = null,
    var description: String? = null,
    var language: String? = null,
    val git_url: String? = null,
    val stargazers_count: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeString(git_url)
        parcel.writeString(stargazers_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Project> {
        override fun createFromParcel(parcel: Parcel): Project {
            return Project(parcel)
        }

        override fun newArray(size: Int): Array<Project?> {
            return arrayOfNulls(size)
        }
    }
}
