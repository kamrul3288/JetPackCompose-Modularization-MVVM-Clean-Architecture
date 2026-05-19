package com.iamkamrul.domain.entity

data class RepoItemEntity(
    val userAvatarUrl: String,
    val userName: String,
    val repoName: String,
    val repoFullName: String,
    val repoDescription: String,
    val language: String,
    val forksCount: Int,
    val stargazersCount: Int,
)
