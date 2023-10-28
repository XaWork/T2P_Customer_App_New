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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.secondaryColor
import me.taste2plate.app.customer.presentation.theme.yellowBannerColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow

@Composable
fun WalletScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContentWalletScreen()
    }
}

@Composable
fun ContentWalletScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        item {
            WalletBalanceInfo()
        }

        items(10) {
            SingleWalletTransaction()

            if (it != 10)
                AppDivider()
        }
    }
}

@Composable
fun WalletBalanceInfo() {
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
                        info = "\n${rupeeSign}234"
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
                        info = "\n234"
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
fun SingleWalletTransaction() {
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
            text = "10 Points",
            fontWeight = FontWeight.Bold
        )
    }

    SpaceBetweenRow(items = items)
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WalletScreenPreview() {
    T2PCustomerAppTheme {
        WalletScreen()
    }
}