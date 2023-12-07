package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.custom.LogRequest

interface AnalyticsRepo {

    suspend fun addLog(logRequest: LogRequest)
}