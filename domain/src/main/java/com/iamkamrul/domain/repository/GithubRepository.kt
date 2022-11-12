package com.iamkamrul.domain.repository

import com.iamkamrul.domain.usecase.ProfileUseCase
import com.iamkamrul.domain.usecase.RepoListUseCase
import com.iamkamrul.entity.ProfileEntity
import com.iamkamrul.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow
import com.iamkamrul.domain.utils.Result


interface GithubRepository {
    suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<List<RepoItemEntity>>>
    suspend fun fetchProfile(params: ProfileUseCase.Params):Flow<Result<ProfileEntity>>
}