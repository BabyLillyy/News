package com.example.news.presentation.utils

import java.text.SimpleDateFormat
import java.util.Locale

class StringUtils {
    companion object {
        fun String.convertDateTime(): String? {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date = dateFormat.parse(this)
                val sdfOutput = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                return date?.let { sdfOutput.format(it) }
            }catch (e: Exception){
                e.printStackTrace()
            }
            return ""
        }
    }
}