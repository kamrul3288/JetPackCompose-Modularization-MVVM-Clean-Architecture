package com.iamkamrul.modularization.di

import com.iamkamrul.data.repoimpl.GithubRepoImpl
import com.iamkamrul.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule{

    @Binds
    fun bindGithubRepository(githubRepoImpl: GithubRepoImpl): GithubRepository

}