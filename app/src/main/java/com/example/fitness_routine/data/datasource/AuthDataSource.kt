package com.example.fitness_routine.data.datasource

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface AuthDataSource {
    suspend fun login()
    suspend fun logout()
    suspend fun delete()
    suspend fun hasUser(): Boolean

    val currentUser: Flow<String>
    val currentUserId: String
}



class AuthDataSourceImpl @Inject constructor(

) : AuthDataSource {
    override suspend fun login() {
//        val signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.your_web_client_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()
//
//        SignInR
//        Firebase.auth.sign
    }

    override suspend fun logout() {
        Firebase.auth.signOut()
    }



    override suspend fun delete() {
        Firebase.auth.currentUser!!.delete().await()
    }

    override suspend fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override val currentUser: Flow<String>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { it.uid }!!)
            }

            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }

        }
    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()


}