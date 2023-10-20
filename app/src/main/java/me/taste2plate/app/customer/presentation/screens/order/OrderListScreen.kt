package me.taste2plate.app.customer.presentation.screens.order

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.ExtraLowPadding
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.HighPadding
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.doneString
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.RedBorderCard
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard

@Composable
fun OrderListScreen(
    onNavigateToOrderDetailsScreen: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "My Orders"
            ) {}
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MyOrderList(
                onClick = {
                    onNavigateToOrderDetailsScreen()
                }
            )

            AppButton(
                text = doneString,
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(ScreenPadding)
            ) {}
        }
    }
}

@Composable
fun MyOrderList(onClick: () -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = SpaceBetweenViews,
            bottom = 70.dp
        )
    ) {
        items(10) {
            SingleOrderInfoItem(
                onClick = {
                    onClick()
                }
            )
        }
    }
}

@Composable
fun SingleOrderInfoItem(
    onClick: () -> Unit
) {
    BlackBorderCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ),
        borderWidth = 0.3.dp
    ) {
        Column(modifier = Modifier.clickable {
            onClick()
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .weight(3f)
                        .padding(ScreenPadding),
                ) {
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.DateRange,
                        info = "24-04-2024",
                        fontSize = 18.sp
                    )
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.Info,
                        info = "T2P-134343124",
                        fontSize = 18.sp
                    )
                    InfoWithIcon(
                        modifier = Modifier.padding(LowPadding),
                        icon = true,
                        imageVector = Icons.Outlined.PlayArrow,
                        info = "OTP : 202423",
                        fontSize = 18.sp
                    )
                }

                DrawableIcon(id = R.drawable.arrow_right, modifier = Modifier.weight(1f))
            }

            Divider()

            Text(
                "Delivered",
                color = ForestGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(ScreenPadding)
            )
        }
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderListScreenPreview() {
    T2PCustomerAppTheme {
        OrderListScreen({})
    }
}