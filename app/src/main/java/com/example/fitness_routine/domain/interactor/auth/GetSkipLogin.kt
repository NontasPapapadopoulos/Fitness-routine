package com.example.fitness_routine.domain.interactor.auth

import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSkipLogin @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Boolean, Unit>(dispatcher) {

    override suspend fun invoke(params: Unit): Boolean {
        return authRepository.getSkipLogin() || authRepository.hasUserLoggedIn()
    }

}