package me.taste2plate.app.customer.presentation.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppDivider

@Composable
fun TimePickerDialog(
    timeSlots: List<String>,
    onDismiss: () -> Unit,
    onItemSelected: (index: Int) -> Unit
) {
    AlertDialog(
        containerColor = screenBackgroundColor.invoke(),
        onDismissRequest = { onDismiss() },
        confirmButton = {},
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                itemsIndexed(timeSlots) { index, timeSlot ->
                    Column(
                        modifier = Modifier.noRippleClickable {
                            onItemSelected(index)
                        },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(timeSlot)
                        AppDivider()
                    }
                }
            }
        })
}


/*
@Preview
@Composable
fun CouponBottomSheetPreview() {
    CouponBottomSheet(coupons = )
}*/
