package me.taste2plate.app.customer.utils

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
            .addHeader("Accept", "Application/JSON")
            .addHeader("Content-Type", "Application/JSON")
        if (!TextUtils.isEmpty(authToken)) {
            builder.addHeader("Authorization", "Bearer $authToken")
        }

        return chain.proceed(builder.build())
    }
}