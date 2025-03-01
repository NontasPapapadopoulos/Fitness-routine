package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.NoteDataEntity
import nondas.pap.fitness_routine.data.util.toDate
import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import nondas.pap.fitness_routine.domain.toTimeStamp


fun NoteDataEntity.toDomain(): NoteDomainEntity = NoteDomainEntity(
    id = id,
    date = date.toDate(),
    text = note
)


fun NoteDomainEntity.toData(): NoteDataEntity = NoteDataEntity(
    id = id,
    date = date.toTimeStamp(),
    note = text
)