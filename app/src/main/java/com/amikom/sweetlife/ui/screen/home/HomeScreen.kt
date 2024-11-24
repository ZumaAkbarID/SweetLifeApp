package com.amikom.sweetlife.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Column {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
