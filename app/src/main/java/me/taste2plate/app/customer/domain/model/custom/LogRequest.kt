package me.taste2plate.app.customer.domain.model.custom

data class LogRequest(
    val category: String = "",
    val token: String = "",
    val type: String,
    val event: String,
    val event_data: String = "",
    val geo_ip: String = "",
    val page_name: String,
    val source: String = "android",
    val user_id: String = "",
    val product_id: String = "",
    val order_id: String = ""
)

class LogType {
    companion object {
        var pageVisit: String = "Page visit"
        var login: String = "Login"
        var addToCart: String = "Add To Cart"
        var addToWishlist: String = "Add To Wishlist"
        var checkout: String = "Checkout"
        var actionPerform: String = "Action Perform"
    }
}

