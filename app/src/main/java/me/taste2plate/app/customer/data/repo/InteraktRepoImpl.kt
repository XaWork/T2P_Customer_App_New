package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.InteraktApi
import me.taste2plate.app.customer.domain.model.tracking.TrackEventModel
import me.taste2plate.app.customer.domain.model.tracking.TrackUserModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.repo.InteraktRepo
import javax.inject.Inject

class InteraktRepoImpl @Inject constructor(
    private val api: InteraktApi
) : InteraktRepo {
    override suspend fun trackUser(data: TrackUserModel): CommonResponse {
        return api.trackUser(data)
    }

    override suspend fun trackEvent(data: TrackEventModel): CommonResponse {
        return api.trackEvents(data)
    }
}