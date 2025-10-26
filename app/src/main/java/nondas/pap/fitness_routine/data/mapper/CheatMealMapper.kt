package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.CheatMealDataEntity
import nondas.pap.fitness_routine.data.util.toDate
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.toTimeStamp


fun CheatMealDataEntity.toDomain(): CheatMealDomainEntity = CheatMealDomainEntity(
    id = id,
    date = reportDate.toDate(),
    text = meal

)


fun CheatMealDomainEntity.toData(): CheatMealDataEntity = CheatMealDataEntity(
    id = id,
    reportDate = date.toTimeStamp(),
    meal = text
)