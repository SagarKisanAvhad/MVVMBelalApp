package com.sagar.mvvmbelalapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.sagar.mvvmbelalapp.data.repository.UserRepository
import com.sagar.mvvmbelalapp.util.ApiException
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


            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)

            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }

            /*if (response.isSuccessful) {
                authListener?.onSuccess(response.body()?.user!!)
            } else {
                authListener?.onFailure("Error code: ${response.code()}")
            }*/
        }


        //val loginResponse = UserRepository().userLogin(email!!, password!!)
        //authListener?.onSuccess(loginResponse)
    }
}