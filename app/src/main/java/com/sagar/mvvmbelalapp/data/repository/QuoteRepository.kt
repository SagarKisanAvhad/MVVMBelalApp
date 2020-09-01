package com.sagar.mvvmbelalapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sagar.mvvmbelalapp.data.db.AppDataBase
import com.sagar.mvvmbelalapp.data.db.entities.Quote
import com.sagar.mvvmbelalapp.data.network.MyApi
import com.sagar.mvvmbelalapp.data.network.SafeApiRequest
import com.sagar.mvvmbelalapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository(
    private val api: MyApi,
    private val db: AppDataBase
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            db.getQuoteDao().insertAllQuotes(quotes)
        }
    }

    private suspend fun fetchQuotes() {
        if (isFetchNeeded()) {
            val quoteResponse = apiRequest { api.quotes() }
            quotes.postValue(quoteResponse.quotes)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private fun isFetchNeeded() = true
}