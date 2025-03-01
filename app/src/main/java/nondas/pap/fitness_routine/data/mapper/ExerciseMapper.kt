package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.ExerciseDataEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity


fun ExerciseDataEntity.toDomain(): ExerciseDomainEntity = ExerciseDomainEntity(
    name = name,
    muscle = muscle,
)


fun ExerciseDomainEntity.toData(): ExerciseDataEntity = ExerciseDataEntity(
    name = name,
    muscle = muscle,
)