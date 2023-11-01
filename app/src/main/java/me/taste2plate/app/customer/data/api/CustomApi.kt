package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.SettingsModel
import retrofit2.http.POST

interface CustomApi {
    @POST("settings")
    suspend fun settings(): SettingsModel
}