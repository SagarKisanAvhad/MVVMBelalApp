package com.sagar.mvvmbelalapp.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.sagar.mvvmbelalapp.data.repository.QuoteRepository
import com.sagar.mvvmbelalapp.util.lazyDeferred

class QuotesViewModel(
    val repository: QuoteRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}

