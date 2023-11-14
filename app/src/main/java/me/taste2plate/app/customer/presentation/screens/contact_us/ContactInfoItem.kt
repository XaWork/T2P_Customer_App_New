package me.taste2plate.app.customer.presentation.screens.contact_us

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class ContactInfoItem(
    val modifier: Modifier = Modifier,
    val isIcon: Boolean = false,
    val icon: ImageVector? = null,
    val id: ContactId? = null,
    val title: String
)
