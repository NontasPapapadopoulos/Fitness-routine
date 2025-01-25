package com.example.fitness_routine.domain.interactor.auth

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.interactor.UnexpectedCredentialsException
import com.example.fitness_routine.domain.repository.AuthRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PerformLogin @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, PerformLogin.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        if (params.credential is CustomCredential &&
            params.credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(params.credential.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

            authRepository.login(firebaseCredential)

        }
        else
            throw UnexpectedCredentialsException()

    }

    data class Params(val credential: Credential)
}