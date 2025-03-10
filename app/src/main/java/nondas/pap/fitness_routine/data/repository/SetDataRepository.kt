package nondas.pap.fitness_routine.data.repository

import nondas.pap.fitness_routine.data.datasource.SetDataSource
import nondas.pap.fitness_routine.data.mapper.toData
import nondas.pap.fitness_routine.data.mapper.toDomain
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SetDataRepository(
    private val setDataSource: SetDataSource
): SetRepository {
    override fun getSets(date: Long): Flow<List<SetDomainEntity>> {
        return setDataSource.getSets(date)
            .map { sets -> sets.map { it.toDomain() } }
    }

    override fun getAllSets(): Flow<List<SetDomainEntity>> {
        return setDataSource.getAllSets()
            .map { sets -> sets.map { it.toDomain() } }
    }


    override suspend fun add(set: SetDomainEntity) {
        setDataSource.add(set.toData())
    }

    override suspend fun delete(set: SetDomainEntity) {
        setDataSource.delete(set.toData())
    }

    override suspend fun update(set: SetDomainEntity) {
        setDataSource.update(set.toData())
    }

}