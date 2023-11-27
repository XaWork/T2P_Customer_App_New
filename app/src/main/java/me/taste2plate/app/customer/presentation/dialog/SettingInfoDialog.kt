package me.taste2plate.app.customer.presentation.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


@Composable
fun SettingInfoDialog(
    checkAvailability: CutOffTimeCheckModel? = null,
    setting: SettingsModel.Result,
    type: SettingDialogType,
    setDate: (date: String, time: String) -> Unit = { date, time -> },
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

    //express use
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val format = SimpleDateFormat("HH:mm")
    val currentTime = format.parse("${format.format(calendar.getTime())}")
    var cut_of_time_first: Date? = null
    var cut_of_time_second: Date? = null

    if(checkAvailability!=null) {
        cut_of_time_first =
            format.parse(checkAvailability.result.expressCutOfTimeFirst)
        cut_of_time_second =
            format.parse(checkAvailability.result.expressCutOfTimeSecond)
        val nightTwelve = format.parse("24:00")
        if (currentTime.before(cut_of_time_first)) {
            setDate(formatter.format(Date()), checkAvailability.result.timeslotFirst)
        } else if (currentTime > cut_of_time_first && currentTime < cut_of_time_second) {
            calendar.add(Calendar.DAY_OF_MONTH + 1, 1)
            setDate(formatter.format(calendar.time), checkAvailability.result.timeslotSecond)
        } else if (currentTime > cut_of_time_second && currentTime < nightTwelve) {
            calendar.add(Calendar.DAY_OF_MONTH + 1, 1)
            setDate(formatter.format(Date()), "Night")
        }
    }

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
        SettingDialogType.Express_Delivery -> appSetting.express.expressPopupTitle
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupTitle
    }
}

private fun getTitleColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupTitleColor
        SettingDialogType.Express_Delivery -> appSetting.express.expressPopupBgColor
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupTitle
    }
}

private fun getSubTitle(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupSubtitle
        SettingDialogType.Express_Delivery -> appSetting.express.expressPopupSubtitle
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupSubtitle
    }
}

private fun getSubTitleColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackSubtitleTitleColor
        SettingDialogType.Express_Delivery -> appSetting.order.orderTrackSubtitleTitleColor
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupTitle
    }
}

private fun getDescription(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupDesctiption
        SettingDialogType.Express_Delivery -> appSetting.express.expressPopupDesctiption
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupDesctiption
    }
}

private fun getDescriptionColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackSubtitleDesctiptionColor
        SettingDialogType.Express_Delivery -> appSetting.order.orderTrackSubtitleDesctiptionColor
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupTitle
    }
}

private fun getBgColor(setting: SettingsModel.Result, type: SettingDialogType): String {
    val appSetting = setting.appSettings
    return when (type) {
        SettingDialogType.Track -> appSetting.order.orderTrackPopupBgColor
        SettingDialogType.Express_Delivery -> appSetting.order.orderTrackPopupBgColor
        SettingDialogType.COD_Digital -> appSetting.cod.codPopupTitle
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
    Track,
    Express_Delivery,
    COD_Digital
}