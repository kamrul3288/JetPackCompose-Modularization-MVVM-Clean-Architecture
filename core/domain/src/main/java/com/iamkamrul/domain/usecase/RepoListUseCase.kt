package com.iamkamrul.domain.usecase

import com.iamkamrul.domain.entity.RepoItemEntity
import com.iamkamrul.domain.outcome.Resource
import com.iamkamrul.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoListUseCase @Inject constructor(
    private val repository: GithubRepository,
) : RemoteUseCase<RepoListUseCase.Params, List<RepoItemEntity>> {

    override fun execute(params: Params): Flow<Resource<List<RepoItemEntity>>> =
        repository.fetchRepoList(params.userName)

    data class Params(val userName: String)
}
