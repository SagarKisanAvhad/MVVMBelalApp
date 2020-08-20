package com.sagar.mvvmbelalapp.data.network.responses

import com.sagar.mvvmbelalapp.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)