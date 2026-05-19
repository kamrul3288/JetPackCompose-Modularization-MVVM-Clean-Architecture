package com.iamkamrul.domain.repository

import com.iamkamrul.domain.entity.ProfileEntity
import com.iamkamrul.domain.entity.RepoItemEntity
import com.iamkamrul.domain.outcome.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun fetchRepoList(userName: String): Flow<Resource<List<RepoItemEntity>>>
    fun fetchProfile(userName: String): Flow<Resource<ProfileEntity>>
}
