package com.iamkamrul.domain.usecase

import com.iamkamrul.domain.entity.ProfileEntity
import com.iamkamrul.domain.outcome.Resource
import com.iamkamrul.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: GithubRepository,
) : RemoteUseCase<ProfileUseCase.Params, ProfileEntity> {

    override fun execute(params: Params): Flow<Resource<ProfileEntity>> =
        repository.fetchProfile(params.userName)

    data class Params(val userName: String)
}
