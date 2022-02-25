package com.zialab.data.repositories

import com.zialab.data.api.ZiaLabApiService
import com.zialab.data.di.ZiaLabServices
import com.zialab.data.mappers.toUserUI
import com.zialab.data.util.BaseRepository
import com.zialab.data.util.DispatcherProvider
import com.zialab.data.util.bodyOrException
import com.zialab.domain.common.Result
import com.zialab.domain.entities.UserUI
import com.zialab.domain.repositories.ReposByUserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ReposByUserRepositoryImpl @Inject constructor(
    @ZiaLabServices
    private val ziaLabApiService: ZiaLabApiService,
    private val dispatcherProvider: DispatcherProvider
) : ReposByUserRepository, BaseRepository() {
    override fun getReposByUser(user: String): Flow<Result<UserUI>> {
        return flow<Result<UserUI>> {
            val response = ziaLabApiService.getNumberReposByUser(
                user,
                "token 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"
            ).bodyOrException()

            emit(Result.Success(response.toUserUI()))
        }.onStart {
            emit(Result.Loading(null))
        }.catch {
            emit(handlerErrorException(throwable = it))
        }.flowOn(dispatcherProvider.io())
    }
}