package com.sagar.mvvmbelalapp

import android.app.Application
import com.sagar.mvvmbelalapp.data.db.AppDataBase
import com.sagar.mvvmbelalapp.data.network.MyApi
import com.sagar.mvvmbelalapp.data.network.NetworkConnectionInterceptor
import com.sagar.mvvmbelalapp.data.preferences.PreferenceProvider
import com.sagar.mvvmbelalapp.data.repository.QuoteRepository
import com.sagar.mvvmbelalapp.data.repository.UserRepository
import com.sagar.mvvmbelalapp.ui.auth.AuthViewModelFactory
import com.sagar.mvvmbelalapp.ui.home.profile.ProfileViewModelFactory
import com.sagar.mvvmbelalapp.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDataBase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuoteRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }

    }
}