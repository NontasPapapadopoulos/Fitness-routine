package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.CardioDataEntity
import nondas.pap.fitness_routine.data.util.toDate
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.toTimeStamp


fun CardioDataEntity.toDomain(): CardioDomainEntity = CardioDomainEntity(
    id = id,
    date = date.toDate(),
    type = type,
    minutes = minutes
)


fun CardioDomainEntity.toData(): CardioDataEntity = CardioDataEntity(
    id = id,
    date = date.toTimeStamp(),
    type = type,
    minutes = minutes
)