package me.taste2plate.app.customer.presentation.screens.order

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.screens.Product
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow

@Composable
fun OrderDetailsScreen() {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "T2P-1341343"
            ) {}
        }
    ) {
        ContentOrderDetailsScreen()
    }
}

@Composable
fun ContentOrderDetailsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(2) {
            SingleOrderItem()
        }
    }
}

@Composable
fun SingleOrderItem() {
    Card(
        modifier = Modifier
            .padding(horizontal = ScreenPadding)
            .fillMaxWidth()
            .padding(vertical = VeryLowSpacing),
        shape = RoundedCornerShape(MediumRoundedCorners),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(ScreenPadding)
            ) {
                val product = Product()
                NetworkImage(
                    image = product.image, modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(MediumRoundedCorners)),
                    contentScale = ContentScale.Crop
                )

                HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(ScreenPadding)
            ) {
                RatingInfoRow(
                    flatOff = "345 x 3",
                    rating = "$rupeeSign 983",
                    showIcon = false
                )
            }
        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderDetailsScreenPreview() {
    T2PCustomerAppTheme {
        OrderDetailsScreen()
    }
}