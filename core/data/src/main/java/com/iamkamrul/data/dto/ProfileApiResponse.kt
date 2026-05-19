package com.iamkamrul.data.dto

data class ProfileApiResponse(
    val avatarUrl: String?,
    val login: String?,
    val name: String?,
    val bio: String?,
    val publicRepos: Int?,
    val followers: Int?,
    val following: Int?,
)

