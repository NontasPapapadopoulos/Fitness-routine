package com.example.fitness_routine.domain.interactor.exercise


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import java.util.UUID
import javax.inject.Inject


class AddExercise @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddExercise.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        val exercise = ExerciseDomainEntity(
            id = UUID.randomUUID().toString(),
            name = params.exerciseName,
            muscle = params.muscle,
            userId = Firebase.auth.currentUser?.uid ?: ""
        )
        return exerciseRepository.add(exercise)
    }


    data class Params(val muscle: Muscle, val exerciseName: String)
}