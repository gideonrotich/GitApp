package com.example.gitapp.presentation.screens.search

import com.example.gitapp.domain.models.User

data class UserState(
    val isLoading:Boolean = false,
    val user:User? = null,
    val error:String = ""
)
