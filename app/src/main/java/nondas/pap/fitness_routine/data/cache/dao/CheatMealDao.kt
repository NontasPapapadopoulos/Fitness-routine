package nondas.pap.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import nondas.pap.fitness_routine.data.entity.CheatMealDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CheatMealDao {

    @Query("SELECT * FROM CheatMeal WHERE date = :date")
    fun getCheatMealsFlow(date: Long): Flow<List<CheatMealDataEntity>>

    @Query("SELECT * FROM CheatMeal")
    fun getCheatMealsFlow(): Flow<List<CheatMealDataEntity>>

    @Query("SELECT COUNT(*) FROM CheatMeal WHERE date = :date")
    fun getNumberOfCheatMeals(date: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(cheatMeal: CheatMealDataEntity)

    @Delete
    suspend fun delete(cheatMeal: CheatMealDataEntity)


    @Update
    suspend fun update(cheatMeal: CheatMealDataEntity)
}