package me.taste2plate.app.customer.presentation.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToHomeScreen: () -> Unit
) {

    val state = viewModel.state
    LaunchedEffect(state) {
        when {
            state.isError && state.message != null -> {
                showToast(state.message)
                viewModel.onEvent(ProfileEvents.UpdateState)
            }

            state.userUpdate -> {
                onNavigateToHomeScreen()
            }
        }
    }

    LaunchedEffect(true){
        viewModel.onEvent(
            ProfileEvents.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in edit profile screen",
                    page_name = "/profile"
                )
            ))
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Edit Profile"
            ) {}
        }
    ) {
        ContentEditProfileScreen(viewModel)
    }
}

@Composable
fun ContentEditProfileScreen(
    viewModel: ProfileViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ScreenPadding)
        ) {
            Text(text = "Basic Details", fontSize = 22.sp, fontWeight = FontWeight.W500)

            Text(
                text = "Enter the information below", fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = SpaceBetweenViews)
            )


            RoundedCornerCard(
                cardColor = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary
                ),
                elevation = LowElevation,
            ) {
                Column(
                    modifier = Modifier.padding(ScreenPadding)
                ) {
                    AppTextField(
                        value = viewModel.fullName,
                        onValueChanged = { viewModel.fullName = it },
                        hint = "Full Name"
                    ) {
                        MaterialIcon(
                            imageVector = Icons.Outlined.AccountBox
                        )
                    }

                    VerticalSpace(space = SpaceBetweenViews)

                    AppTextField(
                        value = viewModel.email,
                        onValueChanged = { viewModel.email = it },
                        hint = "Email",
                        keyboardType = KeyboardType.Email
                    ) {
                        MaterialIcon(
                            imageVector = Icons.Outlined.Email
                        )
                    }

                    VerticalSpace(space = SpaceBetweenViews)

                    AppTextField(
                        value = viewModel.mobile,
                        onValueChanged = { viewModel.mobile = it },
                        hint = "Mobile",
                        keyboardType = KeyboardType.Phone
                    ) {
                        MaterialIcon(
                            imageVector = Icons.Outlined.Phone
                        )
                    }
                }
            }
        }

        if (viewModel.state.isLoading)
            ShowLoading()
        else
            AppButton(
                text = "Save Details", modifier = Modifier.align(
                    Alignment.BottomCenter
                )
            ) {
                viewModel.onEvent(ProfileEvents.EditProfile)
            }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EditProfileScreenPreview() {
    T2PCustomerAppTheme {
        // EditProfileScreen()
    }
}