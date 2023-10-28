package me.taste2plate.app.customer.presentation.screens.contact_us

import androidx.compose.ui.graphics.vector.ImageVector

data class ContactInfoItem(
    val isIcon: Boolean = false,
    val icon: ImageVector? = null,
    val id: Int? = null,
    val title: String
)
