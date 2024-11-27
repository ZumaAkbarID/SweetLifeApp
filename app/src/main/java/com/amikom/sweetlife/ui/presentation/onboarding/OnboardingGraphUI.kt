package com.amikom.sweetlife.ui.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingGraphUI(onboardingModel: OnboardingModel) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = onboardingModel.title,
            modifier = Modifier.fillMaxWidth()
                .padding(23.dp, 0.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(
            modifier = Modifier.fillMaxWidth()
                .size(8.dp)
        )

        Text(
            text = onboardingModel.description,
            modifier = Modifier.fillMaxWidth()
                .padding(23.dp, 0.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(
            modifier = Modifier.size(70.dp)
        )

        Image(
            painter = painterResource(
                id = onboardingModel.image
            ),
            contentDescription = "OnBoardingImage",
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp, 0.dp)
                .size(250.dp),
            alignment = Alignment.Center
        )

        Spacer(
            modifier = Modifier.fillMaxWidth()
                .size(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview1() {
    OnboardingGraphUI(OnboardingModel.FirstPages)
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview2() {
    OnboardingGraphUI(OnboardingModel.SecondPages)
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview3() {
    OnboardingGraphUI(OnboardingModel.ThirdPages)
}