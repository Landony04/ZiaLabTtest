package com.zialab.domain.useCases

import com.zialab.domain.common.Result
import com.zialab.domain.entities.UserUI
import kotlinx.coroutines.flow.Flow

interface ReposByUserUseCase {
    /**
     * This interface is invoke for invokeReposByUser
     */
    fun invokeReposByUser(
        user: String
    ): Flow<Result<UserUI>>
}