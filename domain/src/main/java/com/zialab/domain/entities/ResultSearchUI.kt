package com.zialab.domain.entities

data class ResultSearchUI(
    val totalCount: Int,
    val usersItems: ArrayList<UserUI>
)

data class UserUI(
    val id: Int,
    val username: String,
    val avatarUrl: String
)
