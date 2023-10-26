package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme

@Composable
fun HeadingChip(
    text: String
) {
    Card(
        shape = RoundedCornerShape(MediumRoundedCorners),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text,
            modifier = Modifier
                .padding(horizontal = MediumPadding, vertical = LowPadding)
        )
    }
}

@Composable
fun HeadingChipWithLine(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.background
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Divider(
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = MediumPadding)
        )
        Text(
            text,
            color = textColor,
            maxLines = 1
        )
        Divider(
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = MediumPadding)
        )
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeadingChipWithLinePreview() {
    T2PCustomerAppTheme {
        HeadingChipWithLine("Heading chip")
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeadingChipPreview() {
    T2PCustomerAppTheme {
        HeadingChip("Heading chip")
    }
}