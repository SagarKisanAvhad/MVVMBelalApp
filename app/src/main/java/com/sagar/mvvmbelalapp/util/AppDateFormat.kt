package com.sagar.mvvmbelalapp.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AppDateFormat {
    private const val dateFormat = "dd/MM/yyyy HH:mm:ss"
    val df_Date: DateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
}