package com.zialab.data.di

import com.zialab.data.api.ZiaLabApiService
import com.zialab.data.repositories.ReposByUserRepositoryImpl
import com.zialab.data.repositories.SearchUserRepositoryImpl
import com.zialab.data.util.DispatcherProvider
import com.zialab.domain.repositories.ReposByUserRepository
import com.zialab.domain.repositories.SearchUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Repositories for Search Users -------------------->
     */
    @Provides
    fun provideSearchUserRepository(
        @ZiaLabServices
        ziaLabApiService: ZiaLabApiService,
        dispatcherProvider: DispatcherProvider
    ): SearchUserRepository {
        return SearchUserRepositoryImpl(
            ziaLabApiService,
            dispatcherProvider
        )
    }

    /**
     * Repositories for Repos by user -------------------->
     */
    @Provides
    fun provideReposByUserRepository(
        @ZiaLabServices
        ziaLabApiService: ZiaLabApiService,
        dispatcherProvider: DispatcherProvider
    ): ReposByUserRepository {
        return ReposByUserRepositoryImpl(
            ziaLabApiService,
            dispatcherProvider
        )
    }
}