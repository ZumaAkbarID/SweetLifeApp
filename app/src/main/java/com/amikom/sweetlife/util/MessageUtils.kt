package com.amikom.sweetlife.util

import android.content.Context
import android.widget.Toast

fun showToastMessage(context: Context, message: String, duration: Int) {
    Toast.makeText(context, message, duration).show()
}