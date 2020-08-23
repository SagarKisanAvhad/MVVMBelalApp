package com.sagar.mvvmbelalapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.sagar.mvvmbelalapp.data.repository.UserRepository
import com.sagar.mvvmbelalapp.util.ApiException
import com.sagar.mvvmbelalapp.util.Coroutines
import com.sagar.mvvmbelalapp.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()


    fun onLoginButtonClicked(view: View) {

        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            //error
            authListener?.onFailure("Invalid email or password")
            return
        }

        //success

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)

            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }
}