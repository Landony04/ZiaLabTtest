package com.zialab.domain.useCases

import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import com.zialab.domain.entities.SearchUserRequestUI
import kotlinx.coroutines.flow.Flow

interface SearchUserUseCase {
    /**
     * This interface is invoke for SearchUsers
     */
    fun invokeSearchUser(
        searchUserRequestUI: SearchUserRequestUI
    ): Flow<Result<ResultSearchUI>>
}