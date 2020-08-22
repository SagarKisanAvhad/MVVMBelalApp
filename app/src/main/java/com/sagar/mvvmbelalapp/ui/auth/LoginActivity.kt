package com.sagar.mvvmbelalapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sagar.mvvmbelalapp.R
import com.sagar.mvvmbelalapp.data.db.AppDataBase
import com.sagar.mvvmbelalapp.data.db.entities.User
import com.sagar.mvvmbelalapp.data.network.MyApi
import com.sagar.mvvmbelalapp.data.network.NetworkConnectionInterceptor
import com.sagar.mvvmbelalapp.data.repository.UserRepository
import com.sagar.mvvmbelalapp.databinding.ActivityLoginBinding
import com.sagar.mvvmbelalapp.ui.home.HomeActivity
import com.sagar.mvvmbelalapp.util.hide
import com.sagar.mvvmbelalapp.util.show
import com.sagar.mvvmbelalapp.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        //val viewModel = ViewModelProviders.of(this,AuthViewModel::class.java)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)

        val api = MyApi(networkConnectionInterceptor)
        val db = AppDataBase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val viewModel: AuthViewModel by viewModels { factory }
        viewModel.authListener = this
        binding.viewmodel = viewModel
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            user?.let {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }

        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {

        progress_bar.hide()
        // root_layout.snackbar("${user.name} is logged In")

        /*loginResponse.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })*/
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}