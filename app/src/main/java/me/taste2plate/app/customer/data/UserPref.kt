package me.taste2plate.app.customer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import timber.log.Timber
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_datastore")

class UserPref @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_USER = stringPreferencesKey("user")
        private val KEY_ADDRESS = stringPreferencesKey("address")
        private val KEY_TASTE = stringPreferencesKey("taste")
        private val KEY_IS_LOGIN = booleanPreferencesKey("isLogin")
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    //=====================> TOKEN <=================
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
        getToken()
    }

    suspend fun getToken(): String {
        val tokenFlow = dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }
        Timber.e("Token is ${tokenFlow.first()}")
        return tokenFlow.first() ?: ""
    }


    //=====================> TASTE <=================
    suspend fun setTaste(taste: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TASTE] = taste
        }
        getTaste()
    }

    suspend fun getTaste(): String {
        val taste = dataStore.data.map { preferences ->
            preferences[KEY_TASTE]
        }
        Timber.e("Taste is ${taste.first()}")
        return taste.first() ?: "1"
    }


    //=====================> IS Login <=================
    suspend fun setLogin(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_LOGIN] = isLogin
        }
        getToken()
    }

    suspend fun isLogin(): Boolean {
        val isLogin = dataStore.data.map { preferences ->
            preferences[KEY_IS_LOGIN]
        }
        Timber.e("User is Login${isLogin.first()}")
        return isLogin.first() ?: false
    }


    //=====================> User <=================
    suspend fun saveUser(user: User) {
        val stringUser = Gson().toJson(user)
        dataStore.edit { preferences ->
            preferences[KEY_USER] = stringUser
        }
        getUser()
    }

    suspend fun getUser(): User {
        val userFlow = dataStore.data.map { preferences ->
            preferences[KEY_USER]
        }

        val user: User = Gson().fromJson(userFlow.first(), User::class.java)
        Timber.e("User is ${userFlow.first()}")

        return user
    }


    //=====================> Default address <=================
    suspend fun saveDefaultAddress(address: AddressListModel.Result) {
        val stringAddress = Gson().toJson(address)
        dataStore.edit { preferences ->
            preferences[KEY_ADDRESS] = stringAddress
        }
        getDefaultAddress()
    }

    suspend fun getDefaultAddress(): AddressListModel.Result? {
        val addressFlow = dataStore.data.map { preferences ->
            preferences[KEY_ADDRESS]
        }

        return if (addressFlow.first() == null)
            null
        else {
            val address: AddressListModel.Result =
                Gson().fromJson(addressFlow.first(), AddressListModel.Result::class.java)
            Timber.e("address is ${addressFlow.first()}")

            address
        }
    }

}