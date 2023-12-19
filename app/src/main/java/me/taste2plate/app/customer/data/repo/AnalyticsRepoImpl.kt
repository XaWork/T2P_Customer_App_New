package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.AnalyticsApi
import me.taste2plate.app.customer.domain.model.custom.LogCreatedResponse
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.TrackerResponse
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.repo.AnalyticsRepo
import javax.inject.Inject
import javax.inject.Named

class AnalyticsRepoImpl @Inject constructor(
     private val api: AnalyticsApi
) : AnalyticsRepo {

    override suspend fun addLog(logRequest: LogRequest): LogCreatedResponse {
        return api.addLog("add-log",logRequest)
    }

    override suspend fun install(
        clickId: String,
        security_token: String,
        tracker_record: String,
        gaid: String,
        sub4: String
    ): TrackerResponse {
        return api.install(clickId, tracker_record, security_token, gaid, sub4)
    }
}