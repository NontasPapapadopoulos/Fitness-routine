package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.BodyMeasurementDataEntity
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity


fun BodyMeasurementDomainEntity.toData(): BodyMeasurementDataEntity = BodyMeasurementDataEntity(
    id = id,
    reportDate = date,
    weight = weight,
    fat = fat,
    muscleMass = muscleMass,
    bmi = bmi,
    tbw = tbw,
    bmr = bmr,
    visceralFat = visceralFat,
    metabolicAge = metabolicAge
)


fun BodyMeasurementDataEntity.toDomain(): BodyMeasurementDomainEntity = BodyMeasurementDomainEntity(
    id = id,
    date = reportDate,
    weight = weight,
    fat = fat,
    muscleMass = muscleMass,
    bmi = bmi,
    tbw = tbw,
    bmr = bmr,
    visceralFat = visceralFat,
    metabolicAge = metabolicAge
)