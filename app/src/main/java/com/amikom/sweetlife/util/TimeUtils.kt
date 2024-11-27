package com.amikom.sweetlife.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateTimeString: String): String {
    // Format input string untuk parsing
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Parsing string menjadi objek LocalDateTime
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

    // Mendapatkan nama hari dan bulan dalam locale en
    val dayOfWeek = dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val month = dateTime.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

    // Mendapatkan komponen tanggal, tahun, jam, menit dan detik
    val dayOfMonth = dateTime.dayOfMonth
    val year = dateTime.year
    val hour = dateTime.hour
    val minute = dateTime.minute
    val second = dateTime.second

    return "$dayOfWeek, $dayOfMonth $month $year at : $hour:$minute:$second"
}