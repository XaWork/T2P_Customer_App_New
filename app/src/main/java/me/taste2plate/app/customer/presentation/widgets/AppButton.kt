package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.buttonRoundedShapeCornerRadius
import me.taste2plate.app.customer.presentation.theme.primaryColor

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    customContent: Boolean = false,
    text: String = "",
    maxLines: Int = 1,
    fontSize: TextUnit = TextUnit.Unspecified,
    shape: Shape = RoundedCornerShape(buttonRoundedShapeCornerRadius),
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    paddingValues: PaddingValues = PaddingValues(horizontal = 10.dp),
    customContentItems: @Composable () -> Unit = {},
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
        colors = buttonColors,
        onClick = { onClick() }) {
        if (customContent)
            customContentItems()
        else
            Text(
                text = text.uppercase(),
                fontSize = fontSize,
                maxLines = maxLines
            )
    }
}

@Composable
fun AppOutlineButton(
    modifier: Modifier = Modifier,
    text: String = "",
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = primaryColor.invoke().copy(alpha = 0.1f)
    ),
    shape: Shape = RoundedCornerShape(buttonRoundedShapeCornerRadius),
    paddingValues: PaddingValues = PaddingValues(horizontal = 10.dp),
    fontSize: TextUnit = TextUnit.Unspecified,
    textColor: Color = primaryColor.invoke(),
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
        onClick = { onClick() },
        colors = colors,
        border = BorderStroke(
            width = 0.5.dp,
            color = primaryColor.invoke()
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(0.dp)
    ) {
        Text(text = text.uppercase(), color = textColor, fontSize = fontSize)
    }
}

@Preview
@Composable
fun ButtonPreview() {
    T2PCustomerAppTheme {
        AppOutlineButton(text = "Button") {}
    }
}