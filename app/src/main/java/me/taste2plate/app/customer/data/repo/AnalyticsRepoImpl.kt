package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.AnalyticsApi
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.repo.AnalyticsRepo
import javax.inject.Inject
import javax.inject.Named

class AnalyticsRepoImpl @Inject constructor(
     private val api: AnalyticsApi
) : AnalyticsRepo {

    override suspend fun addLog(logRequest: LogRequest) {
        api.addLog("add-log",logRequest)
    }
}