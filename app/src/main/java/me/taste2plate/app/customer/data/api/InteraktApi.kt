package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.tracking.TrackEventModel
import me.taste2plate.app.customer.domain.model.tracking.TrackUserModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface InteraktApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Basic LTRlZk1kdjFOT05XcHVvQ1lIeDZMbGl5cGxCWjRiRlJ4VUFOUGNPYlRuazo="
    )
    @POST("users/")
    suspend fun trackUser(@Body data: TrackUserModel): CommonResponse

    @Headers(
        "Content-Type: application/json",
        "Authorization: Basic LTRlZk1kdjFOT05XcHVvQ1lIeDZMbGl5cGxCWjRiRlJ4VUFOUGNPYlRuazo="
    )
    @POST("events/")
    suspend fun trackEvents(@Body data: TrackEventModel): CommonResponse
}