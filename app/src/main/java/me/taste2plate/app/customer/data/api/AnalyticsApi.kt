package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.custom.IpAddressResponse
import me.taste2plate.app.customer.domain.model.custom.LogCreatedResponse
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.TrackerResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface AnalyticsApi {
    @POST
    suspend fun addLog(@Url url: String, @Body request: LogRequest): Void

    @POST("add-log")
    suspend fun addLog1(@Body request: LogRequest): LogCreatedResponse

    @POST("acquisition")
    suspend fun install(
        @Query("tracker_record") tracker_record: String,
        @Query("click_id") clickId: String,
        @Query("security_token") security_token: String,
        @Query("gaid") gaid: String,
        @Query("sub4") sub4: String,
        @Query("type") type: String = "install",
    ): TrackerResponse

    @POST("acquisition")
    suspend fun purchased(
        @Query("tracker_record") tracker_record: String,
        @Query("click_id") clickId: String,
        @Query("security_token") security_token: String,
        @Query("gaid") gaid: String,
        @Query("sub4") sub4: String,
        @Query("goal_name ") goal_name : String,
        @Query("sale_amount ") sale_amount : String,
        @Query("type") type: String = "checkout",
    ): Call<TrackerResponse>

    @GET("/?format=json")
    fun getIpAddress(): IpAddressResponse
}