package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews

@Composable
fun AppEmptyView() {

    AppScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            MaterialIcon(
                imageVector =
                Icons.Default.Info,
                tint = Color.LightGray,
                modifier = Modifier
                    .size(100.dp)
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = "No Data Found",
                style = MaterialTheme.typography.titleMedium,
            )


        }
    }
}