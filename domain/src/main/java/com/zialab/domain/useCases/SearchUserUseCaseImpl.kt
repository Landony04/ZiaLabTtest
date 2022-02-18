package com.zialab.domain.useCases

import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import com.zialab.domain.entities.SearchUserRequestUI
import com.zialab.domain.repositories.SearchUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserUseCaseImpl @Inject constructor(private val searchUserRepository: SearchUserRepository) :
    SearchUserUseCase {
    override fun invokeSearchUser(searchUserRequestUI: SearchUserRequestUI): Flow<Result<ResultSearchUI>> =
        searchUserRepository.searchUser(searchUserRequestUI)
}