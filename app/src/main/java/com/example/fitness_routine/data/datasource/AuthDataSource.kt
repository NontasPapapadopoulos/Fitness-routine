package com.example.fitness_routine.data.datasource

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface AuthDataSource {
    suspend fun login(authCredential: AuthCredential)
    suspend fun logout()
    suspend fun delete()
    suspend fun hasUser(): Boolean

    val hasUserFlow: Flow<Boolean>
    val currentUser: Flow<String>
    val currentUserId: String
}



class AuthDataSourceImpl @Inject constructor(

) : AuthDataSource {
    override suspend fun login(authCredential: AuthCredential) {
        com.google.firebase.Firebase.auth.signInWithCredential(authCredential).await()
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

    override val hasUserFlow: Flow<Boolean>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser != null)
            }

            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
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