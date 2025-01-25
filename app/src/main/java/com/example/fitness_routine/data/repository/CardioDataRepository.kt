package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.CardioDataSource
import com.example.fitness_routine.data.datasource.NoteDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.repository.CardioRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardioDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): CardioRepository {

    override fun getCardios(date: Long): Flow<List<CardioDomainEntity>> {
        return flowOf()
    //        return cardioDataSource.getCardios(date).map { cardios ->
//            cardios.map { it.toDomain() }
//        }
    }

    override fun getCardios(): Flow<List<CardioDomainEntity>> {
        return flowOf()
//        return cardioDataSource.getCardios().map { cardios ->
//            cardios.map { it.toDomain() }
//        }
    }

    override suspend fun update(cardio: CardioDomainEntity) {
//        cardioDataSource.update(cardio.toData())
    }

    override suspend fun put(cardio: CardioDomainEntity) {
//        cardioDataSource.put(cardio.toData())
    }

    override suspend fun delete(cardio: CardioDomainEntity) {
//        cardioDataSource.delete(cardio.toData())
    }

    override suspend fun init(date: Long) {
//        cardioDataSource.init(date)
    }


}