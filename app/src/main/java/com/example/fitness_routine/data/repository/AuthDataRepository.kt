package com.example.fitness_routine.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.fitness_routine.data.datasource.AuthDataSource
import com.example.fitness_routine.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val SKIP_LOGIN = booleanPreferencesKey("skip_login")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")



class AuthDataRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val context: Context
): AuthRepository {


    override suspend fun login(authCredential: AuthCredential) {
        authDataSource.login(authCredential)
    }

    override suspend fun logout() {
        authDataSource.logout()
        restoreLogin()
    }


    override suspend fun delete() {
        authDataSource.delete()
    }

    override suspend fun skipLogin() {
        context.dataStore.edit { preference ->
            preference[SKIP_LOGIN] = true
        }
    }

    override suspend fun getSkipLogin(): Boolean {
        return context.dataStore.data
            .map { preferences ->
                preferences[SKIP_LOGIN] ?: false
            }.first()
    }


    override fun hasUserLoggedInFlow(): Flow<Boolean> {
        return authDataSource.hasUserFlow
    }

    override suspend fun hasUserLoggedIn(): Boolean {
        return authDataSource.hasUser()
    }


    private suspend fun restoreLogin() {
        context.dataStore.edit { preference ->
            preference[SKIP_LOGIN] = false
        }
    }

}