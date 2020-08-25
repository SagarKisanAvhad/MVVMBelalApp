package com.sagar.mvvmbelalapp.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.sagar.mvvmbelalapp.data.repository.UserRepository
import com.sagar.mvvmbelalapp.util.ApiException
import com.sagar.mvvmbelalapp.util.Coroutines
import com.sagar.mvvmbelalapp.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordConfirm: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()
    fun onSignUpButtonClicked(view: View) {

        authListener?.onStarted()

        //error
        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Please enter Name")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Please enter email")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter password")
            return
        }

        if (passwordConfirm.isNullOrEmpty()) {
            authListener?.onFailure("Please enter same password")
            return
        }

        //success

        Coroutines.main {
            try {
                val authResponse = repository.signup(name!!, email!!, password!!)
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
    fun onLoginButtonClicked(view: View) {

        authListener?.onStarted()
        //error
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Please enter email")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter password")
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

    fun navigateToSignUpActivity(view: View) {
        Intent(view.context, SignupActivity::class.java).let {
            view.context.startActivity(it)
        }
    }

    fun navigateToSignInActivity(view: View) {
        Intent(view.context, LoginActivity::class.java).let {
            view.context.startActivity(it)
        }
    }
}