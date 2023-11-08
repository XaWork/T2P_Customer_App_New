package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.ZipListModel
import retrofit2.Call
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

    @GET("state-list")
    suspend fun getStateList(): StateListModel


    @GET("city-list-by-state")
    suspend fun fetchCityByState(@Query("state") stateId: String): CityListModel


    @GET("zipcode-by-city")
    suspend fun fetchZipList(@Query("city") cityId:String):ZipListModel
}