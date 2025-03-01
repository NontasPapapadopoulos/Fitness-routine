package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.CardioDao
import com.example.fitness_routine.data.cache.dao.CheatMealDao
import com.example.fitness_routine.data.entity.CardioDataEntity
import com.example.fitness_routine.data.entity.CheatMealDataEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

interface CheatMealDataSource {
    fun getCheatMeals(date: Long): Flow<List<CheatMealDataEntity>>
    fun getCheatMeals(): Flow<List<CheatMealDataEntity>>
    suspend fun put(cheatMeal: CheatMealDataEntity)
    suspend fun update(cheatMeal: CheatMealDataEntity)
    suspend fun delete(cheatMeal: CheatMealDataEntity)
    suspend fun init(date: Long)
}


class CheatMealDataSourceImpl @Inject constructor(
    private val cheatMealDao: CheatMealDao
): CheatMealDataSource {

    override fun getCheatMeals(date: Long): Flow<List<CheatMealDataEntity>> {
        return cheatMealDao.getCheatMealsFlow(date)
    }

    override fun getCheatMeals(): Flow<List<CheatMealDataEntity>> {
        return cheatMealDao.getCheatMealsFlow()
    }

    override suspend fun put(cheatMeal: CheatMealDataEntity) {
        cheatMealDao.put(cheatMeal)
    }

    override suspend fun update(cheatMeal: CheatMealDataEntity) {
        cheatMealDao.update(cheatMeal)
    }

    override suspend fun delete(cheatMeal: CheatMealDataEntity) {
        cheatMealDao.delete(cheatMeal)
    }

    override suspend fun init(date: Long) {
        val hasCheatMeal = cheatMealDao.getNumberOfCheatMeals(date) > 0
        if (!hasCheatMeal) {
            val cheatMeal = CheatMealDataEntity(
                id = UUID.randomUUID().toString(),
                date = date,
                meal = ""
            )

            cheatMealDao.put(cheatMeal)
        }
    }


}