package me.taste2plate.app.customer.domain.use_case.analytics

import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.repo.AnalyticsRepo
import javax.inject.Inject

class AddLogUseCase @Inject constructor(
    private val repo: AnalyticsRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        logRequest: LogRequest
    ) {
        /*repo.addLog(
            logRequest.copy(
                user_id = userPref.getUser().id,
                geo_ip = userPref.getToken()
            )
        )*/
    }
}