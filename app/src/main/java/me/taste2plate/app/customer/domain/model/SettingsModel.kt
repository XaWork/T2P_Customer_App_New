package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName

data class SettingsModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("about_us")
        val aboutUs: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("app_settings")
        val appSettings: AppSettings,
        @SerializedName("cancel_time")
        val cancelTime: String,
        @SerializedName("contact_email")
        val contactEmail: String,
        @SerializedName("contact_phone")
        val contactPhone: String,
        @SerializedName("customer_android_version")
        val customerAndroidVersion: String,
        @SerializedName("customer_ios_version")
        val customerIosVersion: String,
        @SerializedName("dashboard")
        val dashboard: List<Dashboard>,
        @SerializedName("logistic_android_version")
        val logisticAndroidVersion: String,
        @SerializedName("logistic_ios_version")
        val logisticIosVersion: String,
        @SerializedName("maximum_order_value_cod")
        val maximumOrderValueCod: String,
        @SerializedName("minimum_order_value")
        val minimumOrderValue: String,
        @SerializedName("point")
        val point: Point,
        @SerializedName("privacy")
        val privacy: String,
        @SerializedName("product_not_available_message")
        val productNotAvailableMessage: String,
        @SerializedName("refund")
        val refund: String,
        @SerializedName("terms")
        val terms: String,
        @SerializedName("vendor_android_version")
        val vendorAndroidVersion: String,
        @SerializedName("vendor_ios_version")
        val vendorIosVersion: String,
        @SerializedName("whatsapp")
        val whatsapp: String
    ) {
        data class AppSettings(
            @SerializedName("cancel")
            val cancel: Cancel,
            @SerializedName("cod")
            val cod: Cod,
            @SerializedName("delivery_popup")
            val deliveryPopup: DeliveryPopup,
            @SerializedName("express")
            val express: Express,
            @SerializedName("header_bg_color")
            val headerBgColor: String,
            @SerializedName("info_popup")
            val infoPopup: InfoPopup,
            @SerializedName("order")
            val order: Order,
            @SerializedName("popup")
            val popup: Popup,
            @SerializedName("slider")
            val slider: Slider
        ) {
            data class Cancel(
                @SerializedName("cancel_popup_bg_color")
                val cancelPopupBgColor: String,
                @SerializedName("cancel_popup_desctiption")
                val cancelPopupDesctiption: String,
                @SerializedName("cancel_popup_subtitle")
                val cancelPopupSubtitle: String,
                @SerializedName("cancel_popup_title")
                val cancelPopupTitle: String,
                @SerializedName("cancel_popup_title_color")
                val cancelPopupTitleColor: String,
                @SerializedName("cancel_subtitle_desctiption_color")
                val cancelSubtitleDesctiptionColor: String,
                @SerializedName("cancel_subtitle_title_color")
                val cancelSubtitleTitleColor: String
            )

            data class Cod(
                @SerializedName("cod_popup_bg_color")
                val codPopupBgColor: String,
                @SerializedName("cod_popup_desctiption")
                val codPopupDesctiption: String,
                @SerializedName("cod_popup_subtitle")
                val codPopupSubtitle: String,
                @SerializedName("cod_popup_title")
                val codPopupTitle: String,
                @SerializedName("cod_popup_title_color")
                val codPopupTitleColor: String,
                @SerializedName("cod_subtitle_desctiption_color")
                val codSubtitleDesctiptionColor: String,
                @SerializedName("cod_subtitle_title_color")
                val codSubtitleTitleColor: String
            )

            data class DeliveryPopup(
                @SerializedName("delivery_popup_bg_color")
                val deliveryPopupBgColor: String,
                @SerializedName("delivery_popup_desctiption")
                val deliveryPopupDesctiption: String,
                @SerializedName("delivery_popup_subtitle")
                val deliveryPopupSubtitle: String,
                @SerializedName("delivery_popup_title")
                val deliveryPopupTitle: String,
                @SerializedName("delivery_popup_title_color")
                val deliveryPopupTitleColor: String,
                @SerializedName("delivery_subtitle_desctiption_color")
                val deliverySubtitleDesctiptionColor: String,
                @SerializedName("delivery_subtitle_title_color")
                val deliverySubtitleTitleColor: String
            )

            data class Express(
                @SerializedName("express_popup_bg_color")
                val expressPopupBgColor: String,
                @SerializedName("express_popup_desctiption")
                val expressPopupDesctiption: String,
                @SerializedName("express_popup_subtitle")
                val expressPopupSubtitle: String,
                @SerializedName("express_popup_title")
                val expressPopupTitle: String,
                @SerializedName("express_popup_title_color")
                val expressPopupTitleColor: String,
                @SerializedName("express_subtitle_desctiption_color")
                val expressSubtitleDesctiptionColor: String,
                @SerializedName("express_subtitle_title_color")
                val expressSubtitleTitleColor: String
            )

            data class InfoPopup(
                @SerializedName("info_on_off")
                val infoOnOff: String,
                @SerializedName("info_popup_image")
                val infoPopupImage: String,
                @SerializedName("info_popup_text")
                val infoPopupText: String
            )

            data class Order(
                @SerializedName("order_track_popup_bg_color")
                val orderTrackPopupBgColor: String,
                @SerializedName("order_track_popup_desctiption")
                val orderTrackPopupDesctiption: String,
                @SerializedName("order_track_popup_subtitle")
                val orderTrackPopupSubtitle: String,
                @SerializedName("order_track_popup_title")
                val orderTrackPopupTitle: String,
                @SerializedName("order_track_popup_title_color")
                val orderTrackPopupTitleColor: String,
                @SerializedName("order_track_subtitle_desctiption_color")
                val orderTrackSubtitleDesctiptionColor: String,
                @SerializedName("order_track_subtitle_title_color")
                val orderTrackSubtitleTitleColor: String
            )

            data class Popup(
                @SerializedName("popup_bg_color")
                val popupBgColor: String,
                @SerializedName("popup_subtitle")
                val popupSubtitle: String,
                @SerializedName("popup_title")
                val popupTitle: String,
                @SerializedName("popup_title_color")
                val popupTitleColor: String,
                @SerializedName("subtitle_desctiption_color")
                val subtitleDesctiptionColor: String,
                @SerializedName("subtitle_title_color")
                val subtitleTitleColor: String
            )

            data class Slider(
                @SerializedName("service_popup_bg_color")
                val servicePopupBgColor: String,
                @SerializedName("service_popup_desctiption")
                val servicePopupDesctiption: String,
                @SerializedName("service_popup_subtitle")
                val servicePopupSubtitle: String,
                @SerializedName("service_popup_title")
                val servicePopupTitle: String,
                @SerializedName("service_popup_title_color")
                val servicePopupTitleColor: String,
                @SerializedName("service_subtitle_desctiption_color")
                val serviceSubtitleDesctiptionColor: String,
                @SerializedName("service_subtitle_title_color")
                val serviceSubtitleTitleColor: String
            )
        }

        data class Dashboard(
            @SerializedName("background_image")
            val backgroundImage: String,
            @SerializedName("icon")
            val icon: String,
            @SerializedName("title")
            val title: String
        )

        data class Point(
            @SerializedName("cod_digital_payment")
            val codDigitalPayment: String,
            @SerializedName("point_value_in_rupee")
            val pointValueInRupee: String,
            @SerializedName("review")
            val review: String,
            @SerializedName("settings1")
            val settings1: Settings1,
            @SerializedName("settings2")
            val settings2: Settings2,
            @SerializedName("settings3")
            val settings3: Settings3,
            @SerializedName("signup_bonus_reciver")
            val signupBonusReciver: String,
            @SerializedName("signup_bonus_sender")
            val signupBonusSender: String
        ) {
            data class Settings1(
                @SerializedName("max_order")
                val maxOrder: String,
                @SerializedName("min_order")
                val minOrder: String,
                @SerializedName("point")
                val point: String
            )

            data class Settings2(
                @SerializedName("max_order")
                val maxOrder: String,
                @SerializedName("min_order")
                val minOrder: String,
                @SerializedName("point")
                val point: String
            )

            data class Settings3(
                @SerializedName("max_order")
                val maxOrder: String,
                @SerializedName("min_order")
                val minOrder: String,
                @SerializedName("point")
                val point: String
            )
        }
    }
}