package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.WorkoutWithSetsDataEntity
import nondas.pap.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity

fun WorkoutWithSetsDomainEntity.toData(): WorkoutWithSetsDataEntity = WorkoutWithSetsDataEntity(
    workout = workout.toData(),
    sets = sets.map { it.toData() }
)


fun WorkoutWithSetsDataEntity.toDomain(): WorkoutWithSetsDomainEntity = WorkoutWithSetsDomainEntity(
    workout = workout.toDomain(),
    sets = sets.map { it.toDomain() }
)