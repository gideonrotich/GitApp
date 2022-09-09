package com.example.gitapp.presentation.screens.repositories

import com.example.gitapp.domain.models.Repo


data class UserRepositoriesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val repos: List<Repo> = emptyList(),
)
