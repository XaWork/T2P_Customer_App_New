package me.taste2plate.app.customer.domain.model.tracking

data class TrackEventModel(
    val userId: String = "",
    val phoneNumber: String = "",
    val countryCode: String = "+91",
    val event: String = "",
    val traits: EventTraits = EventTraits(),
)

data class EventTraits(
    val productId: String = "",
    val orderId: String = "",
    val quantity: String = "",
    val currency: String = "INR"
)
data class TrackUserModel(
    val userId: String = "",
    val phoneNumber: String = "",
    val countryCode: String = "+91",
    val traits: UserTraits = UserTraits(),
)

data class UserTraits(
    val name: String = "",
    val email: String = "",
)
