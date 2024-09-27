package com.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatDate(originalDate: String, originalPattern: String = "yyyy-MM-dd", newPattern: String = "dd MMMM yyyy"): String {
        return try {
            val originalFormat = SimpleDateFormat(originalPattern, Locale.getDefault())
            val newFormat = SimpleDateFormat(newPattern, Locale.getDefault())
            val date = originalFormat.parse(originalDate)
            newFormat.format(date)
        } catch (e: Exception) {
            originalDate
        }
    }

    fun formatTime(originalTime: String, originalPattern: String = "HH:mm:ss", targetPattern: String = "HH:mm"): String {
        return try {
            val originalFormat = SimpleDateFormat(originalPattern, Locale.getDefault())
            val targetFormat = SimpleDateFormat(targetPattern, Locale.getDefault())
            val time = originalFormat.parse(originalTime) // Parse waktu dari "HH:mm:ss"
            targetFormat.format(time ?: "") // Format ke "HH:mm"
        } catch (e: Exception) {
            originalTime
        }
    }

}