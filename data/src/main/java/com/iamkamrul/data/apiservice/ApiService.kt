package com.iamkamrul.data.apiservice

import com.iamkamrul.apiresponse.ProfileApiResponse
import com.iamkamrul.apiresponse.RepoItemApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/users/{username}/repos")
    suspend fun fetchOwnerRepositoryList(
        @Path("username")username:String
    ): Response<List<RepoItemApiResponse>>

    @GET("/users/{username}")
    suspend fun fetchUser(
        @Path("username")username:String
    ):Response<ProfileApiResponse>
}