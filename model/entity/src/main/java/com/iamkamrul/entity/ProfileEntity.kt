package com.iamkamrul.entity

data class ProfileEntity(
    val userAvatar: String,
    val userFullName: String,
    val userName: String,
    val about: String,
    val repoCount: Int,
    val followerCount: Int,
    val followingCount: Int,
) {
    constructor() : this(
        userAvatar = "https://www.google.com",
        userFullName = "Kamrul Hasan",
        userName = "kamrul3288",
        about = "Android Developer",
        repoCount = 24,
        followerCount = 24,
        followingCount = 24
    )
}
