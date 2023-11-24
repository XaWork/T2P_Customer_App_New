package me.taste2plate.app.customer.domain.model.custom

import androidx.annotation.Keep

@Keep
data class CheckAvailabilityModel(
    val additional_cost: String,
    val cutoff_response: CutoffResponse,
    val express: Boolean,
    val message: String,
    val status: String,
    val cod: Boolean
)
@Keep
data class CutoffResponse(
    val __v: Int,
    val _id: String,
    val active: Int,
    val created_date: String,
    val cut_of_time: String,
    val deleted: Int,
    val end_city: String,
    val express: Boolean,
    val express_cut_of_time_first: String,
    val express_cut_of_time_second: String,
    val express_delivery_cost: Int,
    val express_final_delivery_time_first: String,
    val express_final_delivery_time_second: String,
    val express_remarks: String,
    val final_delivery_time: String,
    val normal_delivery_cost: Int,
    val remarks: String,
    val start_city: String,
    val timeslot: String,
    val timeslot_first: String,
    val timeslot_second: String,
    val update_date: String
)