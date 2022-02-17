package com.zialab.domain.repositories

import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import kotlinx.coroutines.flow.Flow

/**
 * This interface SearchUserRepository inject flow from searchUser
 *
 */
interface SearchUserRepository {
    /**
     * This fun searchUser is use for inject data source
     */
    fun searchUser(
        user: String,
        perPage: Int
    ): Flow<Result<ResultSearchUI>>
}