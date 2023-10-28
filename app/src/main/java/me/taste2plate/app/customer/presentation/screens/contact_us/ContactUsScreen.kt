package me.taste2plate.app.customer.presentation.screens.contact_us

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.MediumSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ContactUsScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContactUsScreenContent()
    }
}

@Composable
fun ContactUsScreenContent() {
    val itemList = listOf(
        ContactInfoItem(
            isIcon = true,
            icon = Icons.Outlined.Phone,
            title = "Call : 7801832728"
        ),
        ContactInfoItem(
            isIcon = true,
            icon = Icons.Outlined.Phone,
            title = "Whatsapp : 7801832728"
        ),
        ContactInfoItem(
            isIcon = true,
            icon = Icons.Outlined.Email,
            title = "support@taste2plate.com"
        ),
        ContactInfoItem(
            isIcon = true,
            icon = Icons.Outlined.LocationOn,
            title = "Address here"
        )
    )

    LazyColumn(
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Customer Support",
                fontSize = 30.sp
            )

            VerticalSpace(space = MediumSpacing)

            DrawableImage(id = R.drawable.logo_new)
        }

        items(itemList) { item ->
            SingleContactInfo(item)
        }

    }
}

@Composable
fun SingleContactInfo(
    info: ContactInfoItem
) {
    Column {
        AppDivider()

        info.run {
            InfoWithIcon(
                icon = isIcon,
                imageVector = icon!!,
                info = title,
                tint = primaryColor.invoke()
            )
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactUsScreenPreview() {
    T2PCustomerAppTheme {
        ContactUsScreen()
    }
}

