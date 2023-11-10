package me.taste2plate.app.customer.presentation.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.onSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.TextAlignCenter
import me.taste2plate.app.customer.presentation.widgets.TextAlignEnd
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToEditProfileScreen: () -> Unit,
    onNavigateToAddressListScreen: () -> Unit,

    ) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Profile"
            ) {}
        }
    ) {
        ContentProfileScreen(
            user = if (viewModel.state.user == null) null else viewModel.state.user,
            onNavigateToEditProfileScreen = {
                onNavigateToEditProfileScreen()
            }
        ) {
            onNavigateToAddressListScreen()
        }
    }
}

@Composable
fun ContentProfileScreen(
    user: User?,
    onNavigateToEditProfileScreen: () -> Unit,
    onNavigateToAddressListScreen: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(ScreenPadding)
    ) {
        Text(text = "Basic Details", fontSize = 22.sp, fontWeight = FontWeight.W500)

        Text(
            text = "Basic Info about you", fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(vertical = SpaceBetweenViews)
        )

        val infoString = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.W400
                )
            ) {
                append(user?.fullName)
            }

            withStyle(SpanStyle()) {
                append("\n${user?.email}\n${user?.mobile}")
            }
        }

        RoundedCornerCard(
            cardColor = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSecondary
            ),
            elevation = LowElevation,
        ) {
            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                Text(infoString)

                VerticalSpace(space = SpaceBetweenViews)

                TextAlignEnd(
                    text = "edit".uppercase(),
                    color = primaryColor.invoke(),
                    modifier = Modifier.clickable {
                        onNavigateToEditProfileScreen()
                    }
                )
            }
        }

        VerticalSpace(space = HighSpacing)

        RoundedCornerCard(
            cardColor = CardDefaults.cardColors(
                containerColor = onSecondaryColor.invoke()
            ),
            elevation = LowElevation,
            modifier = Modifier.clickable {
                onNavigateToAddressListScreen()
            }
        ) {
            TextAlignCenter(
                text = "Your Addresses",
                modifier = Modifier.padding(ScreenPadding)
            )
        }
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    T2PCustomerAppTheme {
        //ProfileScreen({}) {}
    }
}