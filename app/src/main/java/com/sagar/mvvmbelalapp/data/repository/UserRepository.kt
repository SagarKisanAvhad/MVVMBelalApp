package com.sagar.mvvmbelalapp.data.repository

import com.sagar.mvvmbelalapp.data.db.AppDataBase
import com.sagar.mvvmbelalapp.data.db.entities.User
import com.sagar.mvvmbelalapp.data.network.MyApi
import com.sagar.mvvmbelalapp.data.network.SafeApiRequest
import com.sagar.mvvmbelalapp.data.network.responses.AuthResponse

class UserRepository(
    val api: MyApi,
    val db: AppDataBase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}