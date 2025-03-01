package nondas.pap.fitness_routine.domain.repository

import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import kotlinx.coroutines.flow.Flow

interface CheatMealRepository {
    fun getCheatMeals(date: Long): Flow<List<CheatMealDomainEntity>>
    fun getCheatMeals(): Flow<List<CheatMealDomainEntity>>
    suspend fun put(cheatMeal: CheatMealDomainEntity)
    suspend fun update(cheatMeal: CheatMealDomainEntity)
    suspend fun delete(cheatMeal: CheatMealDomainEntity)
    suspend fun init(date: Long)
}