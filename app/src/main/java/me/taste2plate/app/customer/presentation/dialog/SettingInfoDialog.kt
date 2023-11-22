package me.taste2plate.app.customer.presentation.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.domain.model.SettingsModel


@Composable
fun SettingInfoDialog(
    setting: SettingsModel.Result,
    type: SettingDialogType,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val title = getTitle(setting, type)
    val titleColor = getTitleColor(setting, type)
    val subtitle = getSubTitle(setting, type)
    val subtitleColor = getSubTitleColor(setting, type)
    val description = getDescription(setting, type)
    val descriptionColor = getDescriptionColor(setting, type)
    val bgColor = getBgColor(setting, type)

    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = "$subtitle\n\n$description")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Ok")
            }
        }
    )
}

private fun getTitle(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupTitle
    }
}

private fun getTitleColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupTitleColor
    }
}

private fun getSubTitle(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupSubtitle
    }
}

private fun getSubTitleColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackSubtitleTitleColor
    }
}

private fun getDescription(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupDesctiption
    }
}

private fun getDescriptionColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackSubtitleDesctiptionColor
    }
}

private fun getBgColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupBgColor
    }
}

@Preview
@Composable
fun SettingInfoDialogPreview() {
    /*SettingInfoDialog(
        onConfirmation = {},
        onDismissRequest = {}
    )*/
}

enum class SettingDialogType {
    Track
}