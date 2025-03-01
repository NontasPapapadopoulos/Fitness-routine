package nondas.pap.fitness_routine.data.repository

import nondas.pap.fitness_routine.data.datasource.CardioDataSource
import nondas.pap.fitness_routine.data.datasource.NoteDataSource
import nondas.pap.fitness_routine.data.mapper.toData
import nondas.pap.fitness_routine.data.mapper.toDomain
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardioDataRepository @Inject constructor(
    private val cardioDataSource: CardioDataSource
): CardioRepository {

    override fun getCardios(date: Long): Flow<List<CardioDomainEntity>> {
        return cardioDataSource.getCardios(date).map { cardios ->
            cardios.map { it.toDomain() }
        }
    }

    override fun getCardios(): Flow<List<CardioDomainEntity>> {
        return cardioDataSource.getCardios().map { cardios ->
            cardios.map { it.toDomain() }
        }
    }

    override suspend fun update(cardio: CardioDomainEntity) {
        cardioDataSource.update(cardio.toData())
    }

    override suspend fun put(cardio: CardioDomainEntity) {
        cardioDataSource.put(cardio.toData())
    }

    override suspend fun delete(cardio: CardioDomainEntity) {
        cardioDataSource.delete(cardio.toData())
    }

    override suspend fun init(date: Long) {
        cardioDataSource.init(date)
    }


}