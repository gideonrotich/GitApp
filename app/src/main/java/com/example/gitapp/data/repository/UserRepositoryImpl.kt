package com.example.gitapp.data.repository

import com.example.gitapp.data.api.GithubApi
import com.example.gitapp.data.dto.Owner
import com.example.gitapp.data.dto.UserRepoResponseDtoItem
import com.example.gitapp.data.dto.UserResponseDto
import com.example.gitapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api:GithubApi
):UserRepository {
    override suspend fun getUsers(user: String): UserResponseDto {
        return api.getUserProfile(user)
    }

    override suspend fun getUserRepos(username: String): List<UserRepoResponseDtoItem> {
        return api.getUserRepos(username)
    }


}