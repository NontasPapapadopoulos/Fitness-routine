package nondas.pap.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import nondas.pap.fitness_routine.data.entity.SettingsDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("Select * from Settings")
    fun getSettings(): Flow<SettingsDataEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(settings: SettingsDataEntity)

    @Update
    suspend fun update(settings: SettingsDataEntity)

    @Query("UPDATE Settings SET choice = :choice")
    suspend fun changeChoice(choice: String)

    @Query("SELECT COUNT(*) > 0 FROM Settings")
    suspend fun hasSettings(): Boolean

}