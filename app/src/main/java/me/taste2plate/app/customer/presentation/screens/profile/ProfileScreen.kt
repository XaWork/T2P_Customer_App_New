package me.taste2plate.app.customer.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.presentation.dialog.CustomDialog
import me.taste2plate.app.customer.presentation.theme.HighSpacing
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.onSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.TextAlignCenter
import me.taste2plate.app.customer.presentation.widgets.TextAlignEnd
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToEditProfileScreen: () -> Unit,
    onNavigateToAddressListScreen: () -> Unit,
    onNavigateLogoutScreen: () -> Unit,
    navigateBack: () -> Unit,

    ) {

    var showDeleteAccountDialog by remember {
        mutableStateOf(false)
    }

    if (showDeleteAccountDialog) {
        CustomDialog(
            title = "Delete Account?",
            text = "Do you really want to delete your account.",
            confirmButtonText = "Delete",
            dismissButtonText = "Cancel",
            onDismiss = {
                showDeleteAccountDialog = false
            },
            onConfirmation =  {
                showDeleteAccountDialog = false
                viewModel.onEvent(ProfileEvents.DeleteUser)
            }
        )
    }

    LaunchedEffect(true) {
        viewModel.onEvent(
            ProfileEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in profile screen",
                    page_name = "/profile"
                )
            )
        )
    }

    LaunchedEffect(key1 = viewModel.state) {
        if (viewModel.state.userDeleted) {
            onNavigateLogoutScreen()
        }
    }


    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Profile"
            ) { navigateBack() }
        }
    ) {
        ContentProfileScreen(
            state = viewModel.state,
            user = if (viewModel.state.user == null) null else viewModel.state.user,
            onNavigateToEditProfileScreen = {
                onNavigateToEditProfileScreen()
            },
            deleteUser = {
                showDeleteAccountDialog = true
            }
        ) {
            onNavigateToAddressListScreen()
        }
    }
}

@Composable
fun ContentProfileScreen(
    state: ProfileState,
    user: User?,
    onNavigateToEditProfileScreen: () -> Unit,
    deleteUser: () -> Unit,
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
                    modifier = Modifier.noRippleClickable {
                        onNavigateToEditProfileScreen()
                    }
                )
            }
        }

        VerticalSpace(space = SpaceBetweenViews)

        RoundedCornerCard(
            cardColor = CardDefaults.cardColors(
                containerColor = onSecondaryColor.invoke()
            ),
            elevation = LowElevation,
            modifier = Modifier.noRippleClickable {
                onNavigateToAddressListScreen()
            }
        ) {
            TextAlignCenter(
                text = "Your Addresses",
                modifier = Modifier.padding(ScreenPadding)
            )
        }

        VerticalSpace(space = HighSpacing)

        if (state.isLoading)
            ShowLoading()
        else
            RoundedCornerCard(
                cardColor = CardDefaults.cardColors(
                    containerColor = primaryColor.invoke()
                ),
                elevation = LowElevation,
                modifier = Modifier.noRippleClickable {
                    deleteUser()
                }
            ) {
                TextAlignCenter(
                    text = "Delete Account",
                    modifier = Modifier.padding(ScreenPadding)
                )
            }
    }
}
