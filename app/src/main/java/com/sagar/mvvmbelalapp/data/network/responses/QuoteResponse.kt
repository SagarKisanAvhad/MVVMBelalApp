package com.sagar.mvvmbelalapp.data.network.responses

import com.sagar.mvvmbelalapp.data.db.entities.Quote

data class QuoteResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)