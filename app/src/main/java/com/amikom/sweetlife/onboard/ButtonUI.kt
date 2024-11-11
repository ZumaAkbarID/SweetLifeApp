package com.amikom.sweetlife.onboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amikom.sweetlife.R

@Composable
fun ButtonUI(
    text: String = "Next",
    isNext: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    fontSize: Int = 14,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, contentColor = textColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            style = textStyle
        )

        if (isNext) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigate_next_24_white),
                contentDescription = "NextIcon",
                modifier = Modifier.size(20.dp).padding(0.dp)
            )
        }
    }
}

@Preview()
@Composable
fun NextButton() {
    ButtonUI(text = "Next") {

    }
}

@Preview()
@Composable
fun BackButton() {
    ButtonUI(
        text = "Back",
        backgroundColor = Color.DarkGray,
        textColor = Color.LightGray,
        textStyle = MaterialTheme.typography.bodySmall,
        fontSize = 13
    ) {

    }
}
