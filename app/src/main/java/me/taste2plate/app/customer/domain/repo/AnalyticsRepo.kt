package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.custom.LogCreatedResponse
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.TrackerResponse

interface AnalyticsRepo {

    suspend fun addLog(logRequest: LogRequest): LogCreatedResponse

    suspend fun install(
        clickId: String,
        security_token: String,
        tracker_record: String,
        gaid: String,
        sub4: String,
    ): TrackerResponse
}