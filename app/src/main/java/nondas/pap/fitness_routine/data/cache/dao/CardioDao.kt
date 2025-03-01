package nondas.pap.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nondas.pap.fitness_routine.data.entity.CardioDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardioDao {

    @Query("SELECT * FROM cardio WHERE date = :date")
    fun getCardiosFlow(date: Long): Flow<List<CardioDataEntity>>

    @Query("SELECT * FROM cardio")
    fun getCardiosFlow(): Flow<List<CardioDataEntity>>

    @Query("SELECT COUNT(*) FROM cardio WHERE date = :date")
    fun getNumberOfCardios(date: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(cardio: CardioDataEntity)

    @Query("UPDATE cardio SET type = :type, minutes = :minutes WHERE id = :id")
    suspend fun update(id: String, type: String, minutes: String)

    @Delete
    suspend fun delete(cardio: CardioDataEntity)
}