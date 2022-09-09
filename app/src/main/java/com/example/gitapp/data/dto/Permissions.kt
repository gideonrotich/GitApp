package com.example.gitapp.data.dto

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)