package me.taste2plate.app.customer.presentation.screens.wallet

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.WalletTransactionModel
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.secondaryColor
import me.taste2plate.app.customer.presentation.theme.yellowBannerColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow

@Composable
fun WalletScreen(
    viewModel: WalletViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state = viewModel.state
    LaunchedEffect(true){
        viewModel.onEvent(
            WalletEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in wallet screen",
                    page_name = "/wallet"
                )
            ))
    }

    AppScaffold(
        topBar = {
            AppTopBar {navigateBack()}
        },
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.myPlan == null && state.transactions.isEmpty())
            AppEmptyView()
        else
            ContentWalletScreen(state)
    }
}

@Composable
fun ContentWalletScreen(
    state: WalletState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        item {
            if (state.myPlan != null)
                WalletBalanceInfo(state.myPlan)
        }

        itemsIndexed(state.transactions) { index, transaction ->
            SingleWalletTransaction(transaction)

            if (index != state.transactions.size - 1)
                AppDivider()
        }
    }
}

@Composable
fun WalletBalanceInfo(
    plan: MyPlanModel
) {
    RoundedCornerCard {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(300.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(secondaryColor.invoke())
                    .padding(ScreenPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = walletInfoText(
                        title = "Total Balance",
                        info = "\n${plan.walletBalance}"
                    ),
                    color = backgroundColor.invoke(),
                    lineHeight = 35.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(yellowBannerColor.invoke())
                    .padding(ScreenPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = walletInfoText(
                        title = "Total Point Earned",
                        info = "\n${plan.customerPoint}"
                    ),
                    lineHeight = 35.sp
                )
            }
        }
    }
}

@Composable
fun walletInfoText(
    title: String,
    info: String,
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 25.sp
            )
        ) {
            append(title.uppercase())
        }

        withStyle(
            SpanStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(info)
        }
    }
}

@Composable
fun SingleWalletTransaction(
    transaction: WalletTransactionModel.Result
) {
    val items = listOf<@Composable RowScope.() -> Unit> {
        val info = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Points Earned\n")
            }

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Light
                )
            ) {
                append("Earned for signup")
            }
        }
        InfoWithIcon(
            infoAnnotated = info,
            imageVector = Icons.Default.Star,
            icon = true,
            maxLines = 2
        )

        Text(
            text = "${transaction.point} Points",
            fontWeight = FontWeight.Bold
        )
    }

    SpaceBetweenRow(items = items)
}

