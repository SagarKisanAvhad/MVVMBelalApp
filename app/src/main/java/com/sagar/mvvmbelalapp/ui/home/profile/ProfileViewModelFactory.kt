package com.sagar.mvvmbelalapp.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagar.mvvmbelalapp.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}