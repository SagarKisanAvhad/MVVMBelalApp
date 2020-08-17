package com.sagar.mvvmbelalapp.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sagar.mvvmbelalapp.R
import com.sagar.mvvmbelalapp.databinding.ActivityLoginBinding
import com.sagar.mvvmbelalapp.util.toast

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        //val viewModel = ViewModelProviders.of(this,AuthViewModel::class.java)

        val viewModel: AuthViewModel by viewModels()
        viewModel.authListener = this
        binding.viewmodel = viewModel
    }

    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}