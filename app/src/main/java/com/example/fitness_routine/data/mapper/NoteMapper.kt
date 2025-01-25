package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.NoteDataEntity
import com.example.fitness_routine.data.util.toDate
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.toTimeStamp


fun NoteDataEntity.toDomain(): NoteDomainEntity = NoteDomainEntity(
    id = id,
    date = date.toDate(),
    text = note,
    userId = userId
)


fun NoteDomainEntity.toData(): NoteDataEntity = NoteDataEntity(
    id = id,
    date = date.toTimeStamp(),
    note = text,
    userId = userId
)