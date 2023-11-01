package me.taste2plate.app.customer.presentation.screens.splash

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.widgets.AppScaffold

@Composable
fun SplashScreen(
    onNavigateToOnBoardingScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val state = viewModel.state

    LaunchedEffect(state) {
        when {
            state.settings != null -> {
                onNavigateToOnBoardingScreen()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(SplashEvents.GetSettings)
    }

    AppScaffold(
        paddingValues = PaddingValues(0.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Preview(name = "Splash Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashPreviewDark() {
    SplashScreen({}, {})
}