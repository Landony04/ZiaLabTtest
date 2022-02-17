package com.zialab.data.di

import com.example.data.di.ZiaLabServices
import com.zialab.data.api.ZiaLabApiService
import com.zialab.data.repositories.SearchUserRepositoryImpl
import com.zialab.data.util.DispatcherProvider
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
}