package nondas.pap.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import nondas.pap.fitness_routine.data.entity.WorkoutDataEntity


@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(workout: WorkoutDataEntity)

    @Update
    suspend fun update(workout: WorkoutDataEntity)

    @Delete
    suspend fun delete(workout: WorkoutDataEntity)


}