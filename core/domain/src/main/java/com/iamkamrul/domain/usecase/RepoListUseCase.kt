package com.iamkamrul.domain.usecase

import com.iamkamrul.domain.repository.GithubRepository
import com.iamkamrul.domain.utils.ApiUseCaseParams
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoListUseCase @Inject constructor(
    private val repository: GithubRepository
):ApiUseCaseParams<RepoListUseCase.Params,List<RepoItemEntity>>{
    override suspend fun execute(params: Params): Flow<Result<List<RepoItemEntity>>> {
        return repository.fetchRepoList(params)
    }
    data class Params(val userName:String)
}