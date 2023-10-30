package me.taste2plate.app.customer.presentation.screens.checkout

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun OrderConfirmScreen(
    onNavigateToHomeScreen: () -> Unit
) {
    AppScaffold {
        OrderConfirmContent(onNavigateToHomeScreen = onNavigateToHomeScreen)
    }
}

@Composable
fun OrderConfirmContent(
    onNavigateToHomeScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(
                Alignment.Center
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "thank you".uppercase(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor.invoke()
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "For you order".uppercase(),
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "Order number".uppercase(),
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            MaterialIcon(
                imageVector = Icons.Default.CheckCircle,
                tint = primaryColor.invoke(),
                modifier = Modifier
                    .size(200.dp)
            )

            VerticalSpace(space = SpaceBetweenViews)

            Text(
                "estimated delivery".uppercase(),
                fontSize = 25.sp,
                color = primaryColor.invoke()
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "For you order".uppercase(),
            )
        }

        AppButton(
            text = "continue shopping",
            shape = RectangleShape,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(70.dp),
            fontSize = 20.sp
        ) {
            onNavigateToHomeScreen()
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderConfirmScreenPreview() {
    T2PCustomerAppTheme {
        OrderConfirmScreen({})
    }
}