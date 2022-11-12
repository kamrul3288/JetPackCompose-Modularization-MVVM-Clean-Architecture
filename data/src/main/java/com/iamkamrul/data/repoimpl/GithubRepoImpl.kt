package com.iamkamrul.data.repoimpl

import com.iamkamrul.data.apiservice.ApiService
import com.iamkamrul.data.mapper.RepoListItemMapper
import com.iamkamrul.data.utils.NetworkBoundResource
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkBoundResources: NetworkBoundResource,
    private val repositoryListItemMapper: RepoListItemMapper,
    private val profileMapper: ProfileMapper
){

}