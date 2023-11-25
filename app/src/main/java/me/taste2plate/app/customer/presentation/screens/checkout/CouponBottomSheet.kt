package me.taste2plate.app.customer.presentation.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow

@Composable
fun CouponBottomSheet(
    coupons: List<CouponModel.Coupon>,
    applyCoupon: (couponValue: String) -> Unit,
    onItemSelected: (index: Int) -> Unit
) {
    var couponValue by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ScreenPadding)
    ) {
        item {
            val items = listOf<@Composable RowScope.() -> Unit> {
                AppTextField(
                    value = couponValue, onValueChanged = { couponValue = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                )

                AppOutlineButton(
                    text = "Apply",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(start = SpaceBetweenViewsAndSubViews)
                ) {
                    applyCoupon(couponValue)
                }
            }

            SpaceBetweenRow(items = items)

            Text(
                text = "Select Coupon",
                fontSize = 25.sp,
                modifier = Modifier.padding(vertical = ScreenPadding)
            )
        }

        itemsIndexed(coupons) { index, coupon ->
            SingleCouponItem(modifier = Modifier.noRippleClickable {
                onItemSelected(index)
            }, coupon)
        }
    }
}

@Composable
fun SingleCouponItem(
    modifier: Modifier = Modifier,
    coupon: CouponModel.Coupon,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "${coupon.coupon}", color = primaryColor.invoke(),
            fontWeight = FontWeight.Bold
        )

        Text(text = coupon.desc)

        AppDivider()
    }
}

/*
@Preview
@Composable
fun CouponBottomSheetPreview() {
    CouponBottomSheet(coupons = )
}*/
