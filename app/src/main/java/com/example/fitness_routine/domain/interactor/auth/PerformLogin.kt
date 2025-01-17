package com.example.fitness_routine.domain.interactor.auth

import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PerformLogin @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, Unit>(dispatcher) {


    override suspend fun invoke(params: Unit) {
        authRepository.login()
    }


}