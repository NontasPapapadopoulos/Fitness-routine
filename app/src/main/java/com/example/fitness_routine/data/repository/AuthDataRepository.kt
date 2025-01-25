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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

val SKIP_LOGIN = booleanPreferencesKey("skip_login")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")



class AuthDataRepository @Inject constructor(
    private val context: Context
): AuthRepository {


    override suspend fun login(authCredential: AuthCredential) {
        com.google.firebase.Firebase.auth.signInWithCredential(authCredential).await()

    }

    override suspend fun logout() {
        Firebase.auth.signOut()
        restoreLogin()
    }


    override suspend fun delete() {
        Firebase.auth.currentUser!!.delete().await()
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
        return callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser != null)
            }

            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }
    }

    override suspend fun hasUserLoggedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }


    private suspend fun restoreLogin() {
        context.dataStore.edit { preference ->
            preference[SKIP_LOGIN] = false
        }
    }

    suspend fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

}