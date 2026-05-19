package com.iamkamrul.data.mapper

import com.iamkamrul.data.dto.ProfileApiResponse
import com.iamkamrul.domain.entity.ProfileEntity

fun ProfileApiResponse.toEntity(): ProfileEntity = ProfileEntity(
    userAvatar = avatarUrl.orEmpty(),
    userFullName = name.orEmpty(),
    userName = login.orEmpty(),
    about = bio.orEmpty(),
    repoCount = publicRepos ?: 0,
    followerCount = followers ?: 0,
    followingCount = following ?: 0,
)
