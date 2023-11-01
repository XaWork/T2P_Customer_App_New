package me.taste2plate.app.customer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_datastore")

class UserPref @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
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

}