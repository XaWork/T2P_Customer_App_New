package me.taste2plate.app.customer.domain.use_case.interakt

import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.tracking.TrackEventModel
import me.taste2plate.app.customer.domain.repo.InteraktRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrackEventUseCase @Inject constructor(
    private val repo: InteraktRepo,
    private val userPref: UserPref
) {
    suspend fun execute(
        data: TrackEventModel
    )/*: Flow<Resource<CommonResponse>>*/ {
        /*   return flow {
               emit(Resource.Loading(true))*/
        try {
            val user = userPref.getUser()
            repo.trackEvent(
                data.copy(
                    userId = user?.id ?: "",
                    phoneNumber = user?.mobile ?: "",
                )
            )
            // emit(Resource.Success(response))
        } catch (io: IOException) {
            io.printStackTrace()
            // emit(Resource.Error(message = ApiErrorMessages.errorIOException))
        } catch (http: HttpException) {
            http.printStackTrace()
            // emit(Resource.Error(message = ApiErrorMessages.httpException))
        } catch (http: Exception) {
            http.printStackTrace()
            // emit(Resource.Error(message = ApiErrorMessages.eException))
        }
        // }
    }
}