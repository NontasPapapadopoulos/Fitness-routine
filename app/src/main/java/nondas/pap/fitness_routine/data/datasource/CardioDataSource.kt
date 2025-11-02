package nondas.pap.fitness_routine.data.datasource

import nondas.pap.fitness_routine.data.entity.CardioDataEntity
import kotlinx.coroutines.flow.Flow
import nondas.pap.fitness_routine.data.cache.dao.CardioDao
import java.util.UUID
import javax.inject.Inject

interface CardioDataSource {
    fun getCardios(date: Long): Flow<List<CardioDataEntity>>
    fun getCardios(): Flow<List<CardioDataEntity>>
    suspend fun put(cardio: CardioDataEntity)
    suspend fun update(cardio: CardioDataEntity)
    suspend fun delete(cardio: CardioDataEntity)
    suspend fun init(date: Long)
    suspend fun getCardioByDate(fromDate: Long, toDate: Long): List<CardioDataEntity>
}


class CardioDataSourceImpl @Inject constructor(
    private val cardioDao: CardioDao
): CardioDataSource {
    override fun getCardios(date: Long): Flow<List<CardioDataEntity>> {
        return cardioDao.getCardiosFlow(date)
    }

    override fun getCardios(): Flow<List<CardioDataEntity>> {
        return cardioDao.getCardiosFlow()
    }

    override suspend fun put(cardio: CardioDataEntity) {
        cardioDao.put(cardio)
    }

    override suspend fun update(cardio: CardioDataEntity) {
        cardioDao.update(cardio.id, cardio.type, cardio.minutes)
    }

    override suspend fun delete(cardio: CardioDataEntity) {
        cardioDao.delete(cardio)
    }

    override suspend fun init(date: Long) {
        val hasCardio = cardioDao.getNumberOfCardios(date) > 0
        if (!hasCardio) {
            val cardio = CardioDataEntity(
                type = "",
                minutes = "",
                id = UUID.randomUUID().toString(),
                reportDate = date
            )

            cardioDao.put(cardio)
        }
    }

    override suspend fun getCardioByDate(fromDate: Long, toDate: Long): List<CardioDataEntity> {
        return cardioDao.getCardiosByDate(fromDate, toDate)
    }



}