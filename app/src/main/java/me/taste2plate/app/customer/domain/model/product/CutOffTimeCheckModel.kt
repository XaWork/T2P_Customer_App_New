package me.taste2plate.app.customer.domain.model.product


import com.google.gson.annotations.SerializedName

data class CutOffTimeCheckModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("active")
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("cut_of_time")
        val cutOfTime: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("end_city")
        val endCity: String,
        @SerializedName("express")
        val express: Boolean,
        @SerializedName("express_cut_of_time_first")
        val expressCutOfTimeFirst: String,
        @SerializedName("express_cut_of_time_second")
        val expressCutOfTimeSecond: String,
        @SerializedName("express_delivery_cost")
        val expressDeliveryCost: Int,
        @SerializedName("express_final_delivery_time_first")
        val expressFinalDeliveryTimeFirst: String,
        @SerializedName("express_final_delivery_time_second")
        val expressFinalDeliveryTimeSecond: String,
        @SerializedName("express_remarks")
        val expressRemarks: String,
        @SerializedName("final_delivery_time")
        val finalDeliveryTime: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("normal_delivery_cost")
        val normalDeliveryCost: Int,
        @SerializedName("remarks")
        val remarks: String,
        @SerializedName("start_city")
        val startCity: String,
        @SerializedName("timeslot")
        val timeslot: String?,
        @SerializedName("timeslot_first")
        val timeslotFirst: String,
        @SerializedName("timeslot_second")
        val timeslotSecond: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )
}