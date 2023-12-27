package me.taste2plate.app.customer.domain.use_case.analytics

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogCreatedResponse
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.repo.AnalyticsRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddLogUseCase @Inject constructor(
    private val repo: AnalyticsRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        logRequest: LogRequest
    )//: Flow<Resource<LogCreatedResponse>>
    {
        try{
            val user = userPref.getUser()
            val response = repo.addLog(
                logRequest.copy(
                    category = userPref.getReferralInfo()[0],
                    token = userPref.getReferralInfo()[1],
                    user_id = user?.id ?: "",
                    geo_ip = userPref.getIp(),
                    source = "android"
                )
            )
        }catch (e: Exception) {
            e.printStackTrace()
        }
      /*  return flow {
            emit(Resource.Loading(true))
            try {

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
        }*/
    }
}