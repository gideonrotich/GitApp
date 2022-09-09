package com.example.gitapp.data.api

import com.example.gitapp.data.dto.Owner
import com.example.gitapp.data.dto.UserRepoResponseDtoItem
import com.example.gitapp.data.dto.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("/users/{user}")
    suspend fun getUserProfile(
        @Path("user") user: String): UserResponseDto
    @GET("/users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username")username:String):List<UserRepoResponseDtoItem>

}