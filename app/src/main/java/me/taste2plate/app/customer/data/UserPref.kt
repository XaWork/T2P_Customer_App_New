package me.taste2plate.app.customer.data

import android.content.Context
import android.util.Log
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
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_datastore")

class UserPref @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_USER = stringPreferencesKey("user")
        private val KEY_SETTINGS = stringPreferencesKey("settings")
        private val KEY_ADDRESS = stringPreferencesKey("address")
        private val KEY_TASTE = stringPreferencesKey("taste")
        private val KEY_IS_LOGIN = booleanPreferencesKey("isLogin")
        private val KEY_IP = stringPreferencesKey("ip")
        private val KEY_REFERRAL_ID = stringPreferencesKey("referralId")
        private val KEY_REFERRAL_TOKEN = stringPreferencesKey("referralToken")
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
        Log.e("token", "Token is ${tokenFlow.first()}")
        return tokenFlow.first() ?: ""
    }


    //=====================> TASTE <=================
    // 1 -> Veg
    // 0 -> Non-Veg
    suspend fun setTaste() {
        dataStore.edit { preferences ->
            val taste = getTaste()
            preferences[KEY_TASTE] = if (taste == Taste.veg) Taste.nonVeg else Taste.veg
        }
        getTaste()
    }

    suspend fun getTaste(): String {
        val taste = dataStore.data.map { preferences ->
            preferences[KEY_TASTE]
        }
        // Log.e("taste","Taste is ${taste.first()}")
        return taste.first() ?: Taste.veg
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
        // Log.e("user","User is Login${isLogin.first()}")
        return isLogin.first() ?: false
    }

    suspend fun saveIp(ip: String) {
        dataStore.edit { preferences ->
            preferences[KEY_IP] = ip
        }
        getIp()
    }

    suspend fun getIp(): String {
        val value = dataStore.data.map { preferences ->
            preferences[KEY_IP]
        }
        return value.first() ?: ""
    }

    suspend fun saveReferralInfo(id: String, token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_REFERRAL_ID] = id
            preferences[KEY_REFERRAL_TOKEN] = token
        }
    }

    suspend fun getReferralInfo(): List<String> {
        val value = dataStore.data.map { preferences ->
            preferences[KEY_REFERRAL_ID]
        }
        val value2 = dataStore.data.map { preferences ->
            preferences[KEY_REFERRAL_TOKEN]
        }
        return listOf(
            "64abd456600481c67e58853a",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiNjRhYmQzNDRmMmIzYWRmMjUxNWUxMDU0IiwicHJvamVjdCI6IjY0YWJkMzZkZjJiM2FkZjI1MTVlMTA1OSIsImlhdCI6MTY4ODk4MjYxNH0.-4KoXpnK-6Kx4SmaN3yZTSKF0V1q-0695XaF69K3QQM"
        )
    }

    //=====================> User <=================
    suspend fun saveUser(user: User) {
        Log.e("user", "User is ${user}")
        val stringUser = Gson().toJson(user)
        dataStore.edit { preferences ->
            preferences[KEY_USER] = stringUser
        }
        setLogin(true)
        getUser()
    }

    suspend fun getUser(): User? {
        val userFlow = dataStore.data.map { preferences ->
            preferences[KEY_USER]
        }

        return Gson().fromJson(userFlow.first(), User::class.java) ?: null
    }


    //=====================> Settings <=================
    suspend fun saveSettings(setting: SettingsModel.Result) {
        val string = Gson().toJson(setting, SettingsModel.Result::class.java)
        dataStore.edit { preferences ->
            preferences[KEY_SETTINGS] = string
        }
        getSettings()
    }

    suspend fun getSettings(): SettingsModel.Result {
        val flow = dataStore.data.map { preferences ->
            preferences[KEY_SETTINGS]
        }

        val setting: SettingsModel.Result =
            Gson().fromJson(flow.first(), SettingsModel.Result::class.java)
        Log.e("setting", "Setting is ${flow.first()}")

        return setting
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
            //Log.e("address","address is ${addressFlow.first()}")
            address
        }
    }

    suspend fun logOut() {
        val setting = getSettings()
        val token = getToken()
        val ipAddress = getIp();

        dataStore.edit {
            it.clear()
        }

        saveSettings(setting)
        saveToken(token)
        saveIp(ipAddress)
    }

}


class Taste {
    companion object {
        const val veg: String = "1"
        const val nonVeg: String = "0"
    }
}