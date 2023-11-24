package me.taste2plate.app.customer.presentation.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.MediumIcon
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.appNameWithFullForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = appNameWithFullForm,
    tasteVisible: Boolean = false,
    checked: Boolean = false,
    title1: @Composable () -> Unit = {},
    onCheckChange: () -> Unit = {},
    onBackClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            DrawableIconButton(painterResource = R.drawable.back) {
                onBackClick()
            }
        },
        title = {
            if (title.isEmpty())
                title1()
            else
                InfoWithIcon(
                    info = title, id = R.drawable.logo_new,
                    iconOrImageModifier = Modifier.size(MediumIcon),
                    fontSize = 16.sp
                )
        }, actions = {
            if (tasteVisible)
                VegNonVegFilter(
                    checked = checked,
                    onCheckChange = onCheckChange,
                    switchModifier = Modifier.padding(horizontal = SpaceBetweenViewsAndSubViews)
                )
        })
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppTopBarPreview() {
    T2PCustomerAppTheme {
        AppTopBar() {}
    }
}