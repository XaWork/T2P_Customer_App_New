package me.taste2plate.app.customer.presentation.screens.auth.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.navigation.Screens
import me.taste2plate.app.customer.presentation.screens.auth.signin.SignInScreen
import me.taste2plate.app.customer.presentation.screens.auth.signup.SignUpScreen
import me.taste2plate.app.customer.presentation.theme.ExtraHighSpacing
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onNavigateToOtpScreen: () -> Unit,
) {
    val pages = listOf("Sign In", "Sign Up")
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope();
    AppScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpace(space = ExtraHighSpacing)
            DrawableImage(id = R.drawable.logo_new, modifier = Modifier.size(100.dp))
            VerticalSpace(space = SpaceBetweenViews)
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                /*indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    height = 2.dp,
                    color = Color.White
                )
            }*/
            ) {
                pages.forEachIndexed { index, s ->
                    Tab(
                        pagerState.currentPage == index, {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(text = s.uppercase(), color = Color.Black)
                        }
                    )
                }
            }
            VerticalSpace(space = SpaceBetweenViews)
            HorizontalPager(
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> {
                        SignInScreen {
                            onNavigateToOtpScreen()
                        }
                    }

                    1 -> {
                        SignUpScreen {
                            onNavigateToOtpScreen()}
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    T2PCustomerAppTheme {
        OnBoardingScreen{}
    }
}