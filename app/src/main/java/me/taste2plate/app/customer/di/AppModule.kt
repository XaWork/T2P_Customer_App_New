package me.taste2plate.app.customer.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.data.api.AnalyticsApi
import me.taste2plate.app.customer.data.api.CustomApi
import me.taste2plate.app.customer.data.api.GeoIpApi
import me.taste2plate.app.customer.data.api.ProductApi
import me.taste2plate.app.customer.data.api.UserApi
import me.taste2plate.app.customer.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    internal var app: T2PApp? = null

    internal fun AppModule(application: T2PApp) {
        app = application
    }

    @Provides
    @Singleton
    internal fun providesApplication(): T2PApp {
        return app!!
    }

    @AssistedFactory
    interface RetrofitFactory {
        fun create(baseUrl: String): RetrofitBuilder
    }

    class RetrofitBuilder @AssistedInject constructor(
        @Assisted val baseUrl: String,
        private val okHttpClient: OkHttpClient,
        //authInterceptor: AuthInterceptor
    ) {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        val client = okHttpClient.newBuilder()
            //.addInterceptor(authInterceptor)
            .build()

        inline fun <reified T> build(): T {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create()
        }
    }

  /*  @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, authInterceptor: AuthInterceptor): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        val client = okHttpClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    */
    /*
        @Provides
        @Singleton
        fun provideAnalyticsRetrofit(okHttpClient: OkHttpClient, authInterceptor: AuthInterceptor): Retrofit {
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setLenient()
                .create()

            val client = okHttpClient.newBuilder()
                .addInterceptor(authInterceptor)
                .build()

            // Replace "newBaseUrl" with the actual new base URL you want to use
            return Retrofit.Builder()
                .baseUrl("https://api.trap2win.com/admin/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }*/


  /*  @Provides
    @Singleton
    fun provideGeoIpRetrofit(
        okHttpClient: OkHttpClient,
        authInterceptor: AuthInterceptor
    ): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        val client = okHttpClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()

        // Replace "newBaseUrl" with the actual new base URL you want to use
        return Retrofit.Builder()
            .baseUrl("https://api.ipify.org")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }*/

    @Provides
    @Singleton
    fun buildOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        //set timeout
        httpClientBuilder
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)

        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClientBuilder.addInterceptor(loggingInterceptor)
        httpClientBuilder.addNetworkInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("lang", "")
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }

  /*  @Provides
    @Singleton
    fun provideAuthInterceptor(userPref: UserPref): AuthInterceptor {
        val authToken: String = runBlocking(Dispatchers.IO) {
            userPref.getToken()
        }
        return AuthInterceptor(authToken)
    }*/


  /*  @Provides
    @Singleton
    fun provideCustomApi(retrofit: Retrofit): CustomApi {
        return retrofit.create()
    }*/


    @Provides
    @Singleton
    fun provideCustomApi(retrofitFactory: RetrofitFactory): CustomApi {
        return retrofitFactory.create(Constants.baseUrl).build()
    }


   /* @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create()
    }*/


    @Provides
    @Singleton
    fun provideUserApi(retrofitFactory: RetrofitFactory): UserApi {
        return retrofitFactory.create(Constants.baseUrl).build()
    }

    /*@Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create()
    }*/

    @Provides
    @Singleton
    fun provideProductApi(retrofitFactory: RetrofitFactory): ProductApi {
        return retrofitFactory.create(Constants.baseUrl).build()
    }

    /* @Provides
     @Singleton
     fun provideAnalyticsApi(@Named("analyticsBaseUrl") retrofit: Retrofit): AnalyticsApi {
         return retrofit.create()
     }*/

    @Provides
    @Singleton
    fun provideAnalyticsApi(retrofitFactory: RetrofitFactory): AnalyticsApi {
        return retrofitFactory.create(Constants.trap2WinBaseUrl).build()
    }

    @Provides
    @Singleton
    fun provideGeoIpsApi(retrofitFactory: RetrofitFactory): GeoIpApi {
        return retrofitFactory.create(Constants.geoIpBaseUrl).build()
    }

    // --------------
    /* @Singleton
     @Provides
     fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
         return context.dataStore
     }*/
}