package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.custom.IpAddressResponse

interface GeoIpRepo {
    suspend fun getIpAddress(): IpAddressResponse
}