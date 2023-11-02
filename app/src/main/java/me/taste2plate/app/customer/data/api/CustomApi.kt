package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomApi {
    @POST("settings")
    suspend fun settings(): SettingsModel

    @GET("home")
    suspend fun home(
        @Query("taste") taste: String
    ): HomeModel
}