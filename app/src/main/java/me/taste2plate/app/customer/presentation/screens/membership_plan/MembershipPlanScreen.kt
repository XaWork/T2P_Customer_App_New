package me.taste2plate.app.customer.presentation.screens.membership_plan

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.custom.Plan
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.dividerThickness
import me.taste2plate.app.customer.presentation.theme.onBackgroundColor
import me.taste2plate.app.customer.presentation.theme.secondaryColor
import me.taste2plate.app.customer.presentation.theme.yellowBannerColor
import me.taste2plate.app.customer.presentation.utils.loremIpsum
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun MembershipPlanScreen(
    viewModel: MembershipPlanViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state = viewModel.state


    LaunchedEffect(true) {
        viewModel.addLog(
            LogRequest(
                type = LogType.pageVisit,
                event = "enter in member ship plan screen",
                page_name = "/membershipPlan"
            )
        )
    }

    AppScaffold(
        topBar = {
            AppTopBar { navigateBack() }
        },
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.plans.isEmpty())
            AppEmptyView()
        else
            ContentMembershipPlanScreen(state.plans)
    }
}

@Composable
fun ContentMembershipPlanScreen(
    plans: List<Plan>
) {
    LazyColumn(
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        items(plans) { plan ->
            SingleMembershipPlanItem(plan)
        }
    }
}

@Composable
fun SingleMembershipPlanItem(
    plan: Plan
) {
    val items = listOf<@Composable RowScope.() -> Unit> {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = yellowBannerColor.invoke()
                )
                .padding(horizontal = MediumPadding)
                .weight(1f),
        ) {
            Text(
                plan.name.uppercase(),
                color = onBackgroundColor.invoke(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(MediumPadding)
                .weight(2f)
        ) {

            Text(
                "Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = plan.description,
                maxLines = 5,
                fontSize = 12.sp,
                lineHeight = 15.sp
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                "Benefits",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = plan.benefits,
                maxLines = 5,
                fontSize = 12.sp,
                lineHeight = 15.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(secondaryColor.invoke())
                .padding(MediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Plan\nRs ${plan.price}",
                color = backgroundColor.invoke(),
                textAlign = TextAlign.Center
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            AppButton(
                text = "Buy",
                fontSize = 10.sp
            ) {

            }

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                "Validity\n${plan.validityText} days",
                color = backgroundColor.invoke(),
                textAlign = TextAlign.Center
            )
        }
    }

    RoundedCornerCard(
        modifier = Modifier.height(250.dp),
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        SpaceBetweenRow(items = items)
    }
}
