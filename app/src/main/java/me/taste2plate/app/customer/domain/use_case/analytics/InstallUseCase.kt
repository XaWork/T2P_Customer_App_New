package me.taste2plate.app.customer.domain.use_case.analytics

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogCreatedResponse
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.TrackerResponse
import me.taste2plate.app.customer.domain.repo.AnalyticsRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InstallUseCase @Inject constructor(
    private val repo: AnalyticsRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        clickId: String,
        gaid: String,
    ): Flow<Resource<TrackerResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                Log.e("Analytics", "instal api starts ")
                val response = repo.install(
                    clickId,
                    "fc36e1f99d0cfdee7d34",
                    tracker_record = userPref.getReferralInfo()[1],
                    gaid,
                    userPref.getToken()
                )
                Log.e("Analytics", "instal api result is $response")
                emit(Resource.Success(response))
            } catch (io: IOException) {
                io.printStackTrace()
                emit(Resource.Error(message = ApiErrorMessages.errorIOException))
            } catch (http: HttpException) {
                http.printStackTrace()
                emit(Resource.Error(message = ApiErrorMessages.httpException))
            } catch (http: Exception) {
                http.printStackTrace()
                emit(Resource.Error(message = ApiErrorMessages.eException))
            }
        }
    }
}