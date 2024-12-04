package com.amikom.sweetlife.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.amikom.sweetlife.R
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun ConfirmSave(
    openDialogCustom: MutableState<Boolean>,
    title: String? = null,
    message: String? = null,
    buttons: List<Pair<String, () -> Unit>> = listOf(),
    dismissOnBackdropClick: Boolean = true
) {
    Dialog(
        onDismissRequest = {
            if (dismissOnBackdropClick) openDialogCustom.value = false
        }
    ) {
        CustomSaveUI(
            openDialogCustom = openDialogCustom,
            title = title,
            message = message,
            buttons = buttons
        )
    }
}

@Composable
fun CustomSaveUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    title: String? = null,
    message: String? = null,
    buttons: List<Pair<String, () -> Unit>> = listOf()
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier.background(Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Judul opsional
                title?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Pesan opsional
                message?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                buttons.forEach { button ->
                    Button(
                        onClick = button.second,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainBlue
                        )
                    ) {
                        Text(button.first)
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun ConfirmSavePreview() {
    val openDialogCustom = mutableStateOf(true)
    ConfirmSave(
        openDialogCustom = openDialogCustom,
        title = "Title",
        message = "Message",
        buttons = listOf(
            "Button 1" to { },
            "Button 2" to { }
        )
    )
}

