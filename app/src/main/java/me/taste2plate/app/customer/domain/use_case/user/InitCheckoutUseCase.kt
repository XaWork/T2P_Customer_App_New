package me.taste2plate.app.customer.domain.use_case.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.taste2plate.app.customer.data.ApiErrorMessages
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CheckoutModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.GetProfileModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.repo.UserRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InitCheckoutUseCase @Inject constructor(
    private val repo: UserRepo,
    private val userPref: UserPref,
) {
    suspend fun execute(
        walletDiscount: Boolean,
        timeSlot: String,
        date: String,
        deliveryCost: String,
        express: String,
        couponCode: String,
        couponType: String,
        couponAmount: String,
        cartPrice: String,
        tipPrice: String,
        finalPrice: String,
        addCost: String,
        browser: String,
    ): Flow<Resource<CheckoutModel>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val userId = userPref.getUser().id
                val addressId = userPref.getDefaultAddress()!!.id
                val customerCity = userPref.getDefaultAddress()!!.city.id
                val zip = userPref.getDefaultAddress()!!.pincode
                val response = repo.initCheckout(
                    walletDiscount,
                    userId,
                    addressId,
                    timeSlot,
                    date,
                    deliveryCost,
                    express,
                    couponCode,
                    couponType,
                    couponAmount,
                    cartPrice,
                    tipPrice,
                    finalPrice,
                    customerCity,
                    addCost,
                    zip,
                    browser
                )
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