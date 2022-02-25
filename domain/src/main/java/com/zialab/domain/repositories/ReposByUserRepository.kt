package com.zialab.domain.repositories

import com.zialab.domain.common.Result
import com.zialab.domain.entities.UserUI
import kotlinx.coroutines.flow.Flow

interface ReposByUserRepository {
    /**
     * This fun getReposByUser is use for inject data source
     */
    fun getReposByUser(
        user: String
    ): Flow<Result<UserUI>>
}