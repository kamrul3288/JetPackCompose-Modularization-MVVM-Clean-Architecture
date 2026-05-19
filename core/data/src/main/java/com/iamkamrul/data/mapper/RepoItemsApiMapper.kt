package com.iamkamrul.data.mapper

import com.iamkamrul.data.dto.RepoItemApiResponse
import com.iamkamrul.domain.entity.RepoItemEntity

fun List<RepoItemApiResponse>.toEntityList(): List<RepoItemEntity> = map { item ->
    RepoItemEntity(
        userAvatarUrl = item.owner?.avatarUrl.orEmpty(),
        userName = item.owner?.login.orEmpty(),
        repoName = item.name.orEmpty(),
        repoFullName = item.fullName.orEmpty(),
        repoDescription = item.description.orEmpty(),
        language = item.language.orEmpty(),
        forksCount = item.forksCount ?: 0,
        stargazersCount = item.stargazersCount ?: 0,
    )
}
