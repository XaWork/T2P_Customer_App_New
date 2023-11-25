package me.taste2plate.app.customer.presentation.screens.product

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.home.CenterColumn
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun AddReviewBottomSheet(
    save: (rating: Float, review: String) -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    var review by remember {
        mutableStateOf("")
    }
    var myRating by remember { mutableIntStateOf(3) }


    CenterColumn {
        Text(
            text = "Add Review",
            modifier = Modifier
                .background(primaryColor.invoke())
                .fillMaxWidth()
                .padding(ScreenPadding),
            color = backgroundColor.invoke()
        )

        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

        RatingBar(
            currentRating = myRating,
            onRatingChanged = { myRating = it }
        )

        Column(
            modifier = Modifier.padding(ScreenPadding)
        ) {
            /*AppTextField(
                value = name,
                hint = "Your name",
                onValueChanged = { name = it },
            )*/

            AppTextField(
                modifier = Modifier.height(200.dp),
                value = review,
                hint = "Your review",
                onValueChanged = { review = it },
            )

            AppButton(
                text = "Save"
            ) {
                save(myRating.toFloat(), review)
            }
        }
    }
}

@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    iconSize: Dp = 34.dp,
    onRatingChanged: (Int) -> Unit,
) {
    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star
                else Icons.Filled.Star,
                contentDescription = null,
                tint = if (i <= currentRating) primaryColor.invoke()
                else Color.Unspecified,
                modifier = Modifier
                    .noRippleClickable { onRatingChanged(i) }
                    .padding(4.dp)
                    .size(iconSize)
            )
        }
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddReviewBottomSheetPreview() {
    T2PCustomerAppTheme {
        //AddReviewBottomSheet()
    }
}