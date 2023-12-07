package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.GeoIpApi
import me.taste2plate.app.customer.domain.model.custom.IpAddressResponse
import me.taste2plate.app.customer.domain.repo.GeoIpRepo
import javax.inject.Inject
import javax.inject.Named

class GeoIpRepoImpl @Inject constructor(
    @Named("geoIpBaseUrl") private val api: GeoIpApi
) : GeoIpRepo {

    override suspend fun getIpAddress(): IpAddressResponse {
        return api.getIpAddress()
    }
}