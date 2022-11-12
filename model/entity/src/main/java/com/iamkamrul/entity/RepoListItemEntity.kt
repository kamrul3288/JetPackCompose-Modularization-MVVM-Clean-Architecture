package com.iamkamrul.entity

data class RepoListItemEntity(
    val userAvatarUrl:String,
    val userName:String,
    val repoName:String,
    val repoFullName:String,
    val repoDescription:String,
    val language:String,
    val forksCount:Int,
    val stargazers_count:Int,
)
