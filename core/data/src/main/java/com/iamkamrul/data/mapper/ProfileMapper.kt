package com.iamkamrul.data.mapper

import com.iamkamrul.apiresponse.ProfileApiResponse
import com.iamkamrul.data.utils.Mapper
import com.iamkamrul.entity.ProfileEntity
import javax.inject.Inject

class ProfileMapper @Inject constructor():Mapper<ProfileApiResponse,ProfileEntity> {
    override fun mapFromApiResponse(type: ProfileApiResponse): ProfileEntity {
        return ProfileEntity(
            userAvatar = type.avatar_url ?:"https://www.pullrequest.com/blog/github-code-review-service/images/github-logo_hub2899c31b6ca7aed8d6a218f0e752fe4_46649_1200x1200_fill_box_center_2.png",
            userFullName = type.name ?: "NAME_NOT_FOUND",
            userName = type.login ?: "",
            about = type.bio ?: "BIO_NOT_FOUND",
            repoCount = type.public_repos ?: 0,
            followerCount = type.followers ?: 0,
            followingCount = type.following ?: 0
        )
    }
}