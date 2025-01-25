package com.example.fitness_routine.domain.interactor.auth

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HasUserLoggedIn @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<Boolean, Unit>(dispatcher) {

    override fun invoke(params: Unit): Flow<Boolean> {
        return authRepository.hasUserLoggedInFlow()
    }

}