package com.iamkamrul.domain.usecase

import com.iamkamrul.domain.outcome.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase

interface RemoteUseCase<Params, Type> : UseCase {
    fun execute(params: Params): Flow<Resource<Type>>
}

interface RemoteUseCaseNoParams<Type> : UseCase {
    fun execute(): Flow<Resource<Type>>
}
