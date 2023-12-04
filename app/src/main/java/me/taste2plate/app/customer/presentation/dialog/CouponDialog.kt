package me.taste2plate.app.customer.presentation.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppDivider
import me.taste2plate.app.customer.presentation.widgets.AppOutlineButton
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun CouponDialog(
    coupons: List<CouponModel.Coupon>,
    onDismiss: () -> Unit,
    applyCoupon: (couponValue: String) -> Unit,
    onItemSelected: (index: Int) -> Unit
) {
    var couponValue by remember {
        mutableStateOf("")
    }


    AlertDialog(
        containerColor = screenBackgroundColor.invoke(),
        title = {
            val items = listOf<@Composable RowScope.() -> Unit> {
                AppTextField(
                    value = couponValue, onValueChanged = { couponValue = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                )

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .weight(1.5f),
                    onClick = {
                        applyCoupon(couponValue)
                    },
                    content = {
                        Text(
                            "Apply",
                            fontSize = 14.sp
                        )
                    }
                )
            }

            SpaceBetweenRow(items = items)
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    Text(
                        text = "Select Coupon",
                        fontSize = 20.sp,
                    )

                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                }

                itemsIndexed(coupons) { index, coupon ->
                    SingleCouponItem(modifier = Modifier.noRippleClickable {
                        onItemSelected(index)
                    }, coupon)
                }
            }
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {}
    )
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
