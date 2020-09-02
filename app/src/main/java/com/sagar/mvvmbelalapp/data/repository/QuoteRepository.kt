package com.sagar.mvvmbelalapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sagar.mvvmbelalapp.data.db.AppDataBase
import com.sagar.mvvmbelalapp.data.db.entities.Quote
import com.sagar.mvvmbelalapp.data.network.MyApi
import com.sagar.mvvmbelalapp.data.network.SafeApiRequest
import com.sagar.mvvmbelalapp.data.preferences.PreferenceProvider
import com.sagar.mvvmbelalapp.util.AppDateFormat
import com.sagar.mvvmbelalapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit


private const val MINIMUM_INTERVAL = 6

class QuoteRepository(
    private val api: MyApi,
    private val db: AppDataBase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            prefs.saveSavedAt(AppDateFormat.df_Date.format(Date()))
            saveQuotes(it)
        }
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            db.getQuoteDao().insertAllQuotes(quotes)
        }
    }

    private suspend fun fetchQuotes() {

        val lastSavedAt = prefs.getSavedAt()

        if (lastSavedAt == null || isFetchNeeded(lastSavedAt)) {
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

    private fun isFetchNeeded(lastSavedAt: String): Boolean {
        val duration = Date().time - AppDateFormat.df_Date.parse(lastSavedAt).time // ?: Date().time
        return TimeUnit.MILLISECONDS.toHours(duration) > MINIMUM_INTERVAL
    }
}