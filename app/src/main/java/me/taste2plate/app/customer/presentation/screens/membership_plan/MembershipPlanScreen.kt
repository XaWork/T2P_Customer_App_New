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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun MembershipPlanScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContentMembershipPlanScreen()
    }
}

@Composable
fun ContentMembershipPlanScreen() {
    LazyColumn(
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        items(10) {
            SingleMembershipPlanItem()
        }
    }
}

@Composable
fun SingleMembershipPlanItem() {
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
                "Silver".uppercase(),
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
                text = loremIpsum,
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
                text = loremIpsum,
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
                "Plan\nRs 2000",
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
                "Validity\n365 days",
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


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MembershipPlanScreenPreview() {
    T2PCustomerAppTheme {
        MembershipPlanScreen()
    }
}