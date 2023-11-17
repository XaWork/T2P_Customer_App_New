package me.taste2plate.app.customer.presentation.screens.my_plan

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.dividerThickness
import me.taste2plate.app.customer.presentation.theme.secondaryColor
import me.taste2plate.app.customer.presentation.theme.whiteBackgroundButtonColor
import me.taste2plate.app.customer.presentation.theme.yellowBannerColor
import me.taste2plate.app.customer.presentation.utils.loremIpsum
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun MyPlanScreen(
    viewModel: MyPlanViewModel = hiltViewModel()
) {
    val state = viewModel.state
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.myPlan == null || state.myPlan.plan.planName == "")
            AppEmptyView()
        else
            ContentMyPlanScreen(state.myPlan)
    }
}

@Composable
fun ContentMyPlanScreen(
    plan: MyPlanModel
) {
    val items = listOf<@Composable RowScope.() -> Unit> {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(yellowBannerColor.invoke()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(color = secondaryColor.invoke()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = plan.plan.planName,
                    color = backgroundColor.invoke(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(color = yellowBannerColor.invoke()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "OR",
                    color = Color.White,
                )

                AppButton(
                    text = "Upgrade",
                    buttonColors = whiteBackgroundButtonColor.invoke(),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(LowPadding)
                ) {

                }
            }


        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2.5f)
                .padding(LowPadding)
        ) {
            MyPlanInfoCard(
                title = "usages",
                info = plan.plan.planPrice
            )

            MyPlanInfoCard(
                title = "expiry date",
                info = plan.plan.expDate
            )

            MyPlanInfoCard(
                title = "benefits",
                info = ""
            )
        }
    }

    BlackBorderCard(
        modifier = Modifier
            .padding(top = ScreenPadding)
    ) {
        SpaceBetweenRow(items = items)
    }
}

@Composable
fun MyPlanInfoCard(
    title: String = "",
    info: String = "",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title.uppercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        Text(
            text = info,
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        Divider(
            thickness = dividerThickness,
            modifier = Modifier.padding(vertical = MediumPadding)
        )
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyPlanScreenPreview() {
    T2PCustomerAppTheme {
        MyPlanScreen()
    }
}