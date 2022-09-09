package com.example.gitapp.domain.repository

import com.example.gitapp.data.dto.Owner
import com.example.gitapp.data.dto.UserRepoResponseDtoItem
import com.example.gitapp.data.dto.UserResponseDto

interface UserRepository {
    suspend fun getUsers(user:String): UserResponseDto
    suspend fun getUserRepos(username:String):List<UserRepoResponseDtoItem>
}