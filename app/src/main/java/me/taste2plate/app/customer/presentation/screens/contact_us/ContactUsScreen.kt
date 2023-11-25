package me.taste2plate.app.customer.presentation.screens.contact_us

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.MediumSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ContactUsScreen(
    viewModel: ContactUsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state = viewModel.state

    AppScaffold(
        topBar = {
            AppTopBar { navigateBack() }
        },
    ) {
        if (state.setting != null)
            ContactUsScreenContent(state)
    }
}

@Composable
fun ContactUsScreenContent(
    state: ContactUsState
) {
    val context = LocalContext.current
    val itemList = listOf(
        ContactInfoItem(
            id = ContactId.Call,
            isIcon = true,
            icon = Icons.Outlined.Phone,
            title = "Call : ${state.setting?.contactPhone}"
        ),
        ContactInfoItem(
            id = ContactId.Whatsapp,
            isIcon = true,
            icon = Icons.Outlined.Phone,
            title = "Whatsapp : ${state.setting?.whatsapp}"
        ),
        ContactInfoItem(
            id = ContactId.Email,
            isIcon = true,
            icon = Icons.Outlined.Email,
            title = state.setting?.contactEmail.toString()
        ),
        ContactInfoItem(
            id = ContactId.Address,
            isIcon = true,
            icon = Icons.Outlined.LocationOn,
            title = state.setting?.address.toString()
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
            SingleContactInfo(item) {
                when (item.id!!) {
                    ContactId.Call -> {
                        dialPhone(context, state.setting!!.contactPhone)
                    }

                    ContactId.Whatsapp -> {}
                    ContactId.Email -> {
                        sendMail(context, item.title)
                    }

                    ContactId.Address -> {}
                }
            }
        }

    }
}

@Composable
fun SingleContactInfo(
    info: ContactInfoItem,
    onItemClick: () -> Unit
) {
    Column {
        AppDivider()

        info.run {
            InfoWithIcon(
                icon = isIcon,
                imageVector = icon!!,
                info = title,
                maxLines = 5,
                tint = primaryColor.invoke(),
                modifier = Modifier.noRippleClickable { onItemClick() }
            )
        }
    }
}


private fun dialPhone(context: Context, mob: String) {
    var mobileNumber = ""
    for (singleNumber in mob.trim()) {
        if (singleNumber == ' ')
            break
        else
            mobileNumber += singleNumber
    }

    Log.e("mobile", mobileNumber)

    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$mobileNumber")
    context.startActivity(intent)
}

private fun sendMail(context: Context, contactEmail: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contactEmail))
    context.startActivity(Intent.createChooser(intent, "Send Via"))
}


enum class ContactId {
    Call,
    Whatsapp,
    Email,
    Address
}

