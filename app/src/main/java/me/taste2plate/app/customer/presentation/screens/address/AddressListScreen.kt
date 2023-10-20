package me.taste2plate.app.customer.presentation.screens.address

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.HighPadding
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun AddressListScreen(
    onNavigateToEditAddEditScreen: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Profile"
            ) {}
        }
    ) {
        ContentAddressListScreen {
            onNavigateToEditAddEditScreen()
        }
    }
}

@Composable
fun ContentAddressListScreen(
    onNavigateToEditAddEditScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(ScreenPadding),
            contentPadding = PaddingValues(
                top = ScreenPadding,
                start = ScreenPadding,
                bottom = 70.dp,
                end = ScreenPadding
            )
        ) {
            items(10) {
                SingleAddressItem(onEdit = { onNavigateToEditAddEditScreen() }) {}
            }
        }

        AppButton(
            text = "Add New Address",
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        ) { onNavigateToEditAddEditScreen() }
    }
}

@Composable
fun SingleAddressItem(
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = LowElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ScreenPadding)
        ) {
            Text("Full Address")

            VerticalSpace(space = SpaceBetweenViews)

            SpaceBetweenRow(item1 = {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .weight(1f)
                        .padding(end = VeryLowSpacing),
                    text = "Delete"
                ) {
                    onDelete()
                }
            }) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .weight(1f)
                        .padding(start = VeryLowSpacing),
                    text = "Edit"
                ) {
                    onEdit()
                }
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddressListScreenPreview() {
    T2PCustomerAppTheme {
        AddressListScreen {}
    }
}