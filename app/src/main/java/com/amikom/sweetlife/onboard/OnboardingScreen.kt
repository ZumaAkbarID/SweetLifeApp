package com.amikom.sweetlife.onboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onFinished: () -> Unit
) {

    val pages: List<OnboardingModel> = listOf(
        OnboardingModel.FirstPages,
        OnboardingModel.SecondPages,
        OnboardingModel.ThirdPages,
    )

    val pagerState: PagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    val buttonState: State<List<String>> = remember {
        derivedStateOf {
            when(pagerState.currentPage) {
                0 -> listOf("", "Lanjut")
                1 -> listOf("Kembali", "Lanjut")
                2 -> listOf("Kembali", "Selesai")
                else -> listOf("", "")
            }
        }
    }

    Scaffold(
        bottomBar = {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp, 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ButtonUI() {

            }

            IndicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage)

            ButtonUI() {  }
        }
    },
        content = {
            Column(Modifier.padding(it)) {
                HorizontalPager(state = pagerState) { index ->
                    OnboardingGraphUI(onboardingModel = pages[index])
                }
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnBoardingScreen() {

    }
}