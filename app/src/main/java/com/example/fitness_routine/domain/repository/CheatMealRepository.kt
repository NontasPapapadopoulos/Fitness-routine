package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CheatMealRepository {
    fun getCheatMeals(userId: String, date: Date): Flow<List<CheatMealDomainEntity>>
    fun getCheatMeals(userId: String): Flow<List<CheatMealDomainEntity>>
    suspend fun put(cheatMeal: CheatMealDomainEntity)
    suspend fun delete(cheatMeal: CheatMealDomainEntity)
    suspend fun init(userId: String, date: Date)
}