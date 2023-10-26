package me.taste2plate.app.customer.presentation.screens.auth.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.home.widgets.HeadingChipWithLine
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.buttonRoundedShapeCornerRadius
import me.taste2plate.app.customer.presentation.utils.getOtpString
import me.taste2plate.app.customer.presentation.utils.mobileNumber
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onNavigateToOtpScreen: () -> Unit,
) {
    val pages = listOf("Sign In", "Sign Up")
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope();
    var mobile by remember {
        mutableStateOf("")
    }

    AppScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {
                DrawableImage(
                    id = R.drawable.header_bg,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )


                DrawableImage(
                    id = R.drawable.logo_new,
                    modifier = Modifier
                        .align(Alignment.Center)
                )


            }

            VerticalSpace(space = SpaceBetweenViews)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                Text(
                    text = "India's One & Only InterCity Food Delivery App",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                VerticalSpace(space = HighSpacing)

                HeadingChipWithLine(
                    text = "Login/Signup",
                    textColor = Color.Unspecified
                )

                VerticalSpace(space = SpaceBetweenViews)

                AppTextField(
                    value = mobile,
                    onValueChanged = { mobile = it },
                    hint = mobileNumber,
                    leadingIcon = {
                        MaterialIcon(
                            imageVector = Icons.Outlined.Phone
                        )
                    })

                VerticalSpace(space = SpaceBetweenViews)

                AppButton(
                    text = getOtpString,
                ) {
                    onNavigateToOtpScreen()
                }

                VerticalSpace(space = SpaceBetweenViews)

                Text(
                    text = "By continuing, you agree to our terms and conditions",
                    textAlign = TextAlign.Center
                )

            }

            /*TabRow(
                selectedTabIndex = pagerState.currentPage,
                *//*indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    height = 2.dp,
                    color = Color.White
                )
            }*//*
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
                            onNavigateToOtpScreen()
                        }
                    }
                }
            }*/
        }
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    T2PCustomerAppTheme {
        OnBoardingScreen {}
    }
}