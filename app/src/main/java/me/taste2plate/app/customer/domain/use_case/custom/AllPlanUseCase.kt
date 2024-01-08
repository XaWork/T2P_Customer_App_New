package me.taste2plate.app.customer.domain.use_case.custom

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.AllPlanListModel
import me.taste2plate.app.customer.domain.repo.CustomRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AllPlanUseCase @Inject constructor(
    private val repo: CustomRepo,
    private val userPref: UserPref
) {
    suspend fun execute(): Flow<Resource<AllPlanListModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val defaultAddress = userPref.getDefaultAddress()
               // val localAddress = userPref.getAddress()

                val city = defaultAddress?.city?.id /*?: localAddress?.cityId*/
                val response = repo.getPlans(city)
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