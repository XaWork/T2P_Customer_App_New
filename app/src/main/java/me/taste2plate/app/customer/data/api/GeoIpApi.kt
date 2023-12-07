package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.custom.IpAddressResponse
import retrofit2.Call
import retrofit2.http.GET

interface GeoIpApi {

    @GET("/?format=json")
    suspend fun getIpAddress(): IpAddressResponse
}