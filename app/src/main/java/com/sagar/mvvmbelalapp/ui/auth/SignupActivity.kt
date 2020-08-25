package com.sagar.mvvmbelalapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sagar.mvvmbelalapp.R
import com.sagar.mvvmbelalapp.data.db.entities.User
import com.sagar.mvvmbelalapp.databinding.ActivitySignupBinding
import com.sagar.mvvmbelalapp.ui.home.HomeActivity
import com.sagar.mvvmbelalapp.util.hide
import com.sagar.mvvmbelalapp.util.show
import com.sagar.mvvmbelalapp.util.snackbar
import kotlinx.android.synthetic.main.activity_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)
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