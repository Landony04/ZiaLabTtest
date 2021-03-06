package com.zialab.data.di

import com.zialab.domain.repositories.ReposByUserRepository
import com.zialab.domain.repositories.SearchUserRepository
import com.zialab.domain.useCases.ReposByUserUseCase
import com.zialab.domain.useCases.ReposByUserUseCaseImpl
import com.zialab.domain.useCases.SearchUserUseCase
import com.zialab.domain.useCases.SearchUserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    /**
     * SEARCH USER
     * @provides of search USERS
     * @param searchUserRepository
     */
    @Provides
    @ViewModelScoped
    fun provideSearchUser(
        searchUserRepository: SearchUserRepository
    ): SearchUserUseCase = SearchUserUseCaseImpl(searchUserRepository)

    /**
     * SEARCH REPOS BY USER
     * @provides of repos by USERS
     * @param reposByUserRepository
     */
    @Provides
    @ViewModelScoped
    fun provideReposByUser(
        reposByUserRepository: ReposByUserRepository
    ): ReposByUserUseCase = ReposByUserUseCaseImpl(reposByUserRepository)
}