package com.zialab.data.api.models

import com.google.gson.annotations.SerializedName

data class ResultSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val usersItems: ArrayList<UserResponse>
)

data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
