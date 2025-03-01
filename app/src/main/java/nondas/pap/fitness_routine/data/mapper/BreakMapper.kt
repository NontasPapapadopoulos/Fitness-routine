package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.BreakDataEntity
import nondas.pap.fitness_routine.domain.entity.BreakDomainEntity


fun BreakDataEntity.toDomain(): BreakDomainEntity = BreakDomainEntity(
    date = date,
    index = index,
    muscle = muscle,
    exercise = exercise,
    time = time
)


fun BreakDomainEntity.toData(): BreakDataEntity = BreakDataEntity(
    date = date,
    index = index,
    muscle = muscle,
    exercise = exercise,
    time = time
)