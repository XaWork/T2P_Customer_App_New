package me.taste2plate.app.customer.presentation.screens.about

import android.text.Html
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.presentation.screens.home.navigation.DrawerAppScreen
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.ShowLoading


@Composable
fun AboutScreen(
    screen: String,
    viewModel: AboutViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.getData()
    }
    AppScaffold(
        topBar = {
            AppTopBar {
                onBackClick()
            }
        }
    ) {
        val text = when (screen) {
            DrawerAppScreen.About.name -> state.setting?.aboutUs
                ?: ""

            DrawerAppScreen.Terms.name -> state.setting?.terms
                ?: ""

            DrawerAppScreen.Privacy.name -> state.setting?.privacy
            else -> state.setting?.refund
        }

       // Log.e("About", text.toString())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(ScreenPadding)
        ) {
            if (state.isLoading) ShowLoading() else if (!text.isNullOrEmpty()) Text(
                text = Html.fromHtml(
                    text,
                    1
                ).toString()
            )
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    //AboutScreen()
}