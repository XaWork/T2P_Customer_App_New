package me.taste2plate.app.customer.domain.repo

import kotlinx.coroutines.flow.Flow
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel

interface CustomRepo {
    suspend fun settings(): SettingsModel
    suspend fun home(
        taste: String): HomeModel
}