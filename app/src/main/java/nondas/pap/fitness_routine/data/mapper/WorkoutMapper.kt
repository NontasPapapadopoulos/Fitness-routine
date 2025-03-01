package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.WorkoutDataEntity
import nondas.pap.fitness_routine.domain.entity.WorkoutDomainEntity


fun WorkoutDomainEntity.toData(): WorkoutDataEntity = WorkoutDataEntity(
    date = date,
    muscles = muscles
)


fun WorkoutDataEntity.toDomain(): WorkoutDomainEntity = WorkoutDomainEntity(
    date = date,
    muscles = muscles
)