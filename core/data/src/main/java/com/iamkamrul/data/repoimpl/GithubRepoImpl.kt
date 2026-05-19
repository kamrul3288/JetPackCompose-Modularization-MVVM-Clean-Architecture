package com.iamkamrul.data.repoimpl

import com.iamkamrul.data.apiservice.ApiService
import com.iamkamrul.data.client.NetworkBoundResource
import com.iamkamrul.data.mapper.toEntity
import com.iamkamrul.data.mapper.toEntityList
import com.iamkamrul.domain.entity.ProfileEntity
import com.iamkamrul.domain.entity.RepoItemEntity
import com.iamkamrul.domain.outcome.Resource
import com.iamkamrul.domain.outcome.mapResult
import com.iamkamrul.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkBoundResource: NetworkBoundResource,
) : GithubRepository {

    override fun fetchRepoList(userName: String): Flow<Resource<List<RepoItemEntity>>> =
        networkBoundResource.downloadData { apiService.fetchRepoList(userName) }
            .mapResult { it.toEntityList() }


    override fun fetchProfile(userName: String): Flow<Resource<ProfileEntity>> =
        networkBoundResource.downloadData { apiService.fetchProfile(userName) }
            .mapResult { it.toEntity() }
}
