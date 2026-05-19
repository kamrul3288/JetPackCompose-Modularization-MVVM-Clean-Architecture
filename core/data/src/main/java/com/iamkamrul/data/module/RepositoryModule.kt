package com.iamkamrul.data.module

import com.iamkamrul.data.repoimpl.GithubRepoImpl
import com.iamkamrul.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGithubRepository(impl: GithubRepoImpl): GithubRepository
}
