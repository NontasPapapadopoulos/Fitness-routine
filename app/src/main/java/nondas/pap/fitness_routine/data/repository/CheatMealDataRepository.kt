package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.CheatMealDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheatMealDataRepository @Inject constructor(
    private val cheatMealDataSource: CheatMealDataSource
): CheatMealRepository {
    override fun getCheatMeals(date: Long): Flow<List<CheatMealDomainEntity>> {
        return cheatMealDataSource.getCheatMeals(date)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getCheatMeals(): Flow<List<CheatMealDomainEntity>> {
        return cheatMealDataSource.getCheatMeals().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun put(cheatMeal: CheatMealDomainEntity) {
        cheatMealDataSource.put(cheatMeal.toData())
    }

    override suspend fun update(cheatMeal: CheatMealDomainEntity) {
        cheatMealDataSource.update(cheatMeal.toData())
    }

    override suspend fun delete(cheatMeal: CheatMealDomainEntity) {
        cheatMealDataSource.delete(cheatMeal.toData())
    }

    override suspend fun init(date: Long) {
        cheatMealDataSource.init(date)
    }


}