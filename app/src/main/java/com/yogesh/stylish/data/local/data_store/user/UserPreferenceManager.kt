package com.yogesh.stylish.data.local.data_store.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceManager(private val context: Context) {
    companion object {

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preference")
        val ONBOARDING_STATUS_KEY = booleanPreferencesKey("onboarding_completed")
    }

    val getOnboardingStatus: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[ONBOARDING_STATUS_KEY] ?: false
    }

    suspend fun saveOnboardingStatus() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_STATUS_KEY] = true
        }
    }
}