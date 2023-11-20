package com.iamkamrul.domain.usecase

import com.iamkamrul.domain.repository.GithubRepository
import com.iamkamrul.domain.utils.ApiUseCaseParams
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: GithubRepository
):ApiUseCaseParams<ProfileUseCase.Params, ProfileEntity>{
    data class Params(val userName:String)
    override suspend fun execute(params: Params): Flow<Result<ProfileEntity>> {
        return repository.fetchProfile(params)
    }
}