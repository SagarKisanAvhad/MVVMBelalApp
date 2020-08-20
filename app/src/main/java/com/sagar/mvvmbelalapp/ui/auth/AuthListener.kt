package com.sagar.mvvmbelalapp.ui.auth

import com.sagar.mvvmbelalapp.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}