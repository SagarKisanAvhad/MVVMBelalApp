package com.sagar.mvvmbelalapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sagar.mvvmbelalapp.data.db.entities.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quote")
    fun getQuotes(): LiveData<List<Quote>>
}