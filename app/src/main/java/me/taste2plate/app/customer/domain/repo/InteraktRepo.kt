package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.tracking.TrackEventModel
import me.taste2plate.app.customer.domain.model.tracking.TrackUserModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse

interface InteraktRepo {

    suspend fun trackUser(data: TrackUserModel): CommonResponse

    suspend fun trackEvent(data: TrackEventModel): CommonResponse
}