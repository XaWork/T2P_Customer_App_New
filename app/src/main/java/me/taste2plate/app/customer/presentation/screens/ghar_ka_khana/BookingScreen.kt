package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.ExtraLowElevation
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppCheckBox
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppDropDown
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun BookingScreen() {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Ghar ka khana"
            ) {}
        }
    ) {
        ContentBookingScreen()
    }
}

@Composable
fun ContentBookingScreen() {
    LazyColumn(
        modifier = Modifier.padding(ScreenPadding)
    ) {
        item {
            PdLocation()

            AppDivider(thickness = 2.dp)

            FoodInfo()

            AppDivider(thickness = 2.dp)
        }

        items(4) {
            SingleFoodItem()
        }

        item {
            AppDivider(thickness = 2.dp)

            DeliveryInfo()

            AppButton(text = "Book Now") {}
        }
    }
}

@Composable
fun PdLocation() {
    SinglePdLocation(
        backgroundColor = primaryColor.invoke(),
        title = "Pickup Location",
        iconId = R.drawable.pickuplocation_icon,
        onClick = {}
    )

    VerticalSpace(space = SpaceBetweenViewsAndSubViews)

    SinglePdLocation(
        backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer,
        title = "Destination Location",
        iconId = R.drawable.destination_icon,
        onClick = {}
    )
}

@Composable
fun SinglePdLocation(
    backgroundColor: Color,
    title: String = "",
    iconId: Int,
    onClick: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = ExtraLowElevation
    ) {
        val items = listOf<@Composable RowScope.() -> Unit> {
            InfoWithIcon(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor
                    )
                    .padding(vertical = ScreenPadding, horizontal = MediumPadding)
                    .weight(1f)
                    .clickable { onClick() },
                iconOrImageModifier = Modifier.size(50.dp),
                id = iconId,
                info = title.uppercase(),
                colorFilter = ColorFilter.tint(screenBackgroundColor.invoke()),
                textColor = screenBackgroundColor.invoke(),
                maxLines = 2,
                fontSize = 14.sp,

                )

            Text(
                "Address",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = MediumPadding),
            )
        }

        SpaceBetweenRow(items = items)
    }
}

@Composable
fun FoodInfo() {
    var productName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val items = listOf<@Composable RowScope.() -> Unit> {
        AppDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            expanded = false,
            hint = "Category",
            onExpandedChange = {},
            selectedText = "",
            onTextChanged = {})

        HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

        AppDropDown(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            hint = "Sub Category",
            expanded = false,
            onExpandedChange = {},
            selectedText = "",
            onTextChanged = {})
    }

    Column {
        SpaceBetweenRow(items = items)

        AppTextField(
            value = productName,
            onValueChanged = {
                productName = it
            },
            hint = "Product Name"
        )

        val items = listOf<@Composable RowScope.() -> Unit> {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = weight,
                onValueChanged = {
                    weight = it
                },
                hint = "Weight(Kg)"
            )

            HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = "Add"
            ) {

            }
        }

        SpaceBetweenRow(items = items)
    }

}

@Composable
fun SingleFoodItem(
    buttonVisible: Boolean = true
) {
    RoundedCornerCard(
        modifier = Modifier
            .padding(bottom = VeryLowSpacing),
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = ExtraLowElevation
    ) {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
                .fillMaxWidth(),
        ) {
            Text(
                "Homemade food (biryani",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            val items = listOf<@Composable RowScope.() -> Unit> {
                Text("Weight : 0.5 kg")

                if (buttonVisible)
                    Row {
                        AppOutlineButton(
                            modifier = Modifier
                                .height(35.dp)
                                .width(80.dp),/*
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue.copy(alpha = 0.1f),
                            contentColor = Color.Blue,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = Color.Blue
                        ),
                        textColor = Color.Blue,*/
                            text = "Edit",
                            shape = CircleShape
                        ) {}

                        HorizontalSpace(space = VeryLowSpacing)

                        AppButton(
                            modifier = Modifier
                                .height(35.dp)
                                .width(100.dp),
                            text = "Delete",
                            shape = CircleShape
                        ) {}
                    }
            }

            SpaceBetweenRow(items = items)
        }
    }
}

@Composable
fun DeliveryInfo() {

    var remarks by remember {
        mutableStateOf("")
    }
    Column {
        AppTextField(
            value = "2",
            onValueChanged = {},
            hint = "Total Weight (kg)",
            readOnly = true
        )

        AppTextField(
            value = remarks,
            onValueChanged = {
                remarks = it
            },
            hint = "Remarks",
            readOnly = true
        )


        val items = listOf<@Composable RowScope.() -> Unit> {
            AppDropDown(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                hint = "Pickup Date",
                expanded = false,
                onExpandedChange = {},
                selectedText = "",
                onTextChanged = {})

            HorizontalSpace(space = LowSpacing)

            AppDropDown(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                hint = "Pickup Time",
                expanded = false,
                onExpandedChange = {},
                selectedText = "",
                onTextChanged = {})
        }

        SpaceBetweenRow(items = items)

        AppCheckBox(
            onCheckedChange = {},
            text = "I agree with Terms conditions and Privacy Policy"
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        BookingScreen()
    }
}