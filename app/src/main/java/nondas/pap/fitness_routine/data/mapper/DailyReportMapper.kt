package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.util.convertToString
import nondas.pap.fitness_routine.data.entity.DailyReportDataEntity
import nondas.pap.fitness_routine.data.util.convertMuscleListToString
import nondas.pap.fitness_routine.data.util.toDate
import nondas.pap.fitness_routine.data.util.toList
import nondas.pap.fitness_routine.data.util.toMusclesList
import nondas.pap.fitness_routine.data.util.toTimeStamp

import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity


fun DailyReportDataEntity.toDomain(): DailyReportDomainEntity = DailyReportDomainEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained.toMusclesList(),
    date = date.toDate(),
)



fun DailyReportDomainEntity.toData(): DailyReportDataEntity = DailyReportDataEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained.convertMuscleListToString(),
    date = date.toTimeStamp(),
)
