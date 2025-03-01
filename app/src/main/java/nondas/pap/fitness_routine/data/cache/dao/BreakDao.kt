package nondas.pap.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import nondas.pap.fitness_routine.data.entity.BreakDataEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BreakDao {

    @Query("SELECT * FROM break WHERE date = :date")
    fun getBreaks(date: Long): Flow<List<BreakDataEntity>>

}