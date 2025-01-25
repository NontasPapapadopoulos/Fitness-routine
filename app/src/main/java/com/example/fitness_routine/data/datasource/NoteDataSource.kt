package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.entity.NoteDataEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

interface NoteDataSource {
    fun getNotes(date: Long): Flow<List<NoteDataEntity>>
    fun getNotes(): Flow<List<NoteDataEntity>>
    suspend fun put(note: NoteDataEntity)
    suspend fun update(note: NoteDataEntity)
    suspend fun delete(note: NoteDataEntity)
    suspend fun init(date: Long)
}

//
//class NoteDataSourceImpl @Inject constructor(
//   // private val noteDao: NoteDao
//): NoteDataSource {
//    override fun getNotes(date: Long): Flow<List<NoteDataEntity>> {
//        return noteDao.getNotesFlow(date)
//    }
//
//    override fun getNotes(): Flow<List<NoteDataEntity>> {
//        return noteDao.getNotesFlow()
//    }
//
//    override suspend fun put(note: NoteDataEntity) {
//        noteDao.put(note)
//    }
//
//    override suspend fun update(note: NoteDataEntity) {
//        noteDao.update(note.note, note.id)
//    }
//
//    override suspend fun delete(note: NoteDataEntity) {
//        noteDao.delete(note)
//    }
//
//    override suspend fun init(date: Long) {
//        val hasNote = noteDao.getNumberOfNotes(date) > 0
//        if (!hasNote) {
//            val note = NoteDataEntity(
//                note = "",
//                date = date,
//                id = UUID.randomUUID().toString()
//            )
//
//            noteDao.put(note)
//        }
//    }
//}