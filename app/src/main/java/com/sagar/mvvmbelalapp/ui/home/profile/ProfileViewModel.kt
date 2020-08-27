package com.sagar.mvvmbelalapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.sagar.mvvmbelalapp.data.repository.UserRepository

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {
    val user = repository.getUser()
}