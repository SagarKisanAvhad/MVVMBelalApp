package com.sagar.mvvmbelalapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.sagar.mvvmbelalapp.data.repository.UserRepository
import com.sagar.mvvmbelalapp.util.Coroutines

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun onLoginButtonClicked(view: View) {

        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            //error
            authListener?.onFailure("Invalid email or password")
            return
        }

        //success

        Coroutines.main {

            val response = UserRepository().userLogin(email!!, password!!)

            if (response.isSuccessful) {
                authListener?.onSuccess(response.body()?.user!!)
            } else {
                authListener?.onFailure("Error code: ${response.code()}")
            }
        }


        //val loginResponse = UserRepository().userLogin(email!!, password!!)
        //authListener?.onSuccess(loginResponse)
    }
}