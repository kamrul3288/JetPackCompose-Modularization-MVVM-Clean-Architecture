package com.iamkamrul.data.repoimpl

import com.iamkamrul.data.apiservice.ApiService
import com.iamkamrul.data.mapper.ProfileMapper
import com.iamkamrul.data.mapper.RepoListItemMapper
import com.iamkamrul.data.utils.NetworkBoundResource
import com.iamkamrul.data.utils.mapFromApiResponse
import com.iamkamrul.domain.repository.GithubRepository
import com.iamkamrul.domain.usecase.ProfileUseCase
import com.iamkamrul.domain.usecase.RepoListUseCase
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.ProfileEntity
import com.iamkamrul.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkBoundResources: NetworkBoundResource,
    private val repositoryListItemMapper: RepoListItemMapper,
    private val profileMapper: ProfileMapper
):GithubRepository{

    override suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<List<RepoItemEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchRepoList(params.userName)
            },repositoryListItemMapper
        )
    }

    override suspend fun fetchProfile(params: ProfileUseCase.Params): Flow<Result<ProfileEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchProfile(params.userName)
            },profileMapper
        )
    }

}