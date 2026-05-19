package com.iamkamrul.data.dto


data class RepoItemApiResponse(
    val name: String?,
    val fullName: String?,
    val description: String?,
    val language: String?,
    val forksCount: Int?,
    val stargazersCount: Int?,
    val owner: RepoOwner?,
)

data class RepoOwner(
    val login: String?,
    val avatarUrl: String?,
)

