package me.taste2plate.app.customer.domain.repo

import kotlinx.coroutines.flow.Flow
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.ZipListModel

interface CustomRepo {
    suspend fun settings(): SettingsModel
    suspend fun home(
        taste: String): HomeModel
    suspend fun stateList(): StateListModel
    suspend fun cityList(
        stateId: String
    ): CityListModel
    suspend fun zipList(
        cityId: String
    ): ZipListModel
}