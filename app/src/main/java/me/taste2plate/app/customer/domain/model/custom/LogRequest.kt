package me.taste2plate.app.customer.domain.model.custom

data class LogRequest(
    val category: String,
    val token: String,
    val type: String,
    val event: String,
    val event_data: String = "",
    val geo_ip: String = "",
    val page_name: String,
    val source: String = "android",
    val user_id: String,
    val product_id: String = "",
    val order_id: String = ""
)
