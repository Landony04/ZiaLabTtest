package com.zialab.domain.useCases

import com.zialab.domain.common.Result
import com.zialab.domain.entities.UserUI
import com.zialab.domain.repositories.ReposByUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReposByUserUseCaseImpl @Inject constructor(private val reposByUserRepository: ReposByUserRepository) :
    ReposByUserUseCase {
    override fun invokeReposByUser(user: String): Flow<Result<UserUI>> =
        reposByUserRepository.getReposByUser(user)
}