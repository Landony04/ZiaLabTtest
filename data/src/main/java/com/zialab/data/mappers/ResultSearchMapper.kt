package com.zialab.data.mappers

import com.zialab.data.api.models.ResultSearchResponse
import com.zialab.data.api.models.UserResponse
import com.zialab.domain.entities.ResultSearchUI
import com.zialab.domain.entities.UserUI

fun ResultSearchResponse.toResultResponseUI(): ResultSearchUI {
    return ResultSearchUI(
        totalCount = this.totalCount,
        usersItems = ArrayList(this.usersItems.map { it.toUserUI() })
    )
}

fun UserResponse.toUserUI(): UserUI {
    return UserUI(
        id = this.id,
        username = this.username,
        avatarUrl = this.avatarUrl
    )
}