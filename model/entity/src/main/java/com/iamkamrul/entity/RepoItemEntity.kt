package com.iamkamrul.entity

data class RepoItemEntity(
    val userAvatarUrl:String,
    val userName:String,
    val repoName:String,
    val repoFullName:String,
    val repoDescription:String,
    val language:String,
    val forksCount:Int,
    val stargazers_count:Int,
){
    constructor():this(
        userAvatarUrl = "https://www.google.com/",
        userName = "Kamrul Hasan",
        repoName = "Kamrul3288",
        repoFullName = "Kamrul3288/JetpackCompose",
        repoDescription = "It's an awsome repository",
        language = "Kotlin",
        forksCount = 100,
        stargazers_count = 786
    )
}