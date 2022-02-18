package com.zialab.searchgithub

import com.zialab.data.di.NetworkModule
import com.zialab.data.di.RepositoryModule
import com.zialab.data.di.UseCaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, UseCaseModule::class, RepositoryModule::class])
interface ApplicationComponent {
}