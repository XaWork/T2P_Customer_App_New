package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.theme.HighPadding
import me.taste2plate.app.customer.presentation.theme.HighRoundedCorners
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.primaryColor

@Composable
fun TextAlignEnd(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified

) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
        color = color
    )
}
@Composable
fun TextAlignCenter(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified

) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color
    )
}

@Composable
fun TextInCircle(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 22.sp
) {
    Text(
        text = text,
        modifier = modifier
            .border(
                1.dp,
                color = primaryColor.invoke(),
                shape = RoundedCornerShape(HighRoundedCorners)
            )
            .padding(
                horizontal = HighPadding,
                vertical = LowPadding
            ),
        color = primaryColor.invoke(),
        textAlign = TextAlign.Center,
        fontSize = fontSize
    )

}

@Composable
fun HeadingText(
    text: String = "",
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.W400
    )
}