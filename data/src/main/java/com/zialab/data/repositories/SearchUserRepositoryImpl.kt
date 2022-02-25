package com.zialab.data.repositories

import com.zialab.data.api.ZiaLabApiService
import com.zialab.data.di.ZiaLabServices
import com.zialab.data.mappers.toResultResponseUI
import com.zialab.data.util.BaseRepository
import com.zialab.data.util.DispatcherProvider
import com.zialab.data.util.bodyOrException
import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import com.zialab.domain.entities.SearchUserRequestUI
import com.zialab.domain.repositories.SearchUserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchUserRepositoryImpl @Inject constructor(
    @ZiaLabServices
    private val ziaLabApiService: ZiaLabApiService,
    private val dispatcherProvider: DispatcherProvider
) : SearchUserRepository, BaseRepository() {
    override fun searchUser(searchUserRequestUI: SearchUserRequestUI): Flow<Result<ResultSearchUI>> {
        return flow<Result<ResultSearchUI>> {
            val response = ziaLabApiService.getGitHubUsers(
                searchUserRequestUI.user,
                1,
                searchUserRequestUI.perPage,
                "token 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"
            ).bodyOrException()

            emit(Result.Success(response.toResultResponseUI()))
        }.onStart {
            emit(Result.Loading(null))
        }.catch {
            emit(handlerErrorException(throwable = it))
        }.flowOn(dispatcherProvider.io())
    }
}