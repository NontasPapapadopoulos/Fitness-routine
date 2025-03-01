package nondas.pap.fitness_routine.data.cache

import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migrations {


    class Migrate4to5: AutoMigrationSpec

    companion object {
        val allMigrations = arrayOf<Migration>(
            object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) = with(db) {

                    // 1. Create new CheatMeal and Note tables
                    execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS CheatMeal (
                            id TEXT NOT NULL PRIMARY KEY,
                            date INTEGER NOT NULL,
                            meal TEXT NOT NULL
                        )
                    """.trimIndent()
                    )


                    execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS Note (
                            id TEXT NOT NULL PRIMARY KEY,
                            date INTEGER NOT NULL,
                            note TEXT NOT NULL
                        )
                    """.trimIndent()
                    )

                    // 2. Copy data from DailyReport to the new tables (if the old fields existed)
                    // Assuming `id` can be generated using UUID
                    execSQL(
                        """
                        INSERT INTO CheatMeal (id, date, meal)
                        SELECT 
                            printf('%s_%d', 'cheatmeal', date),  -- Generate unique IDs
                            date,
                            cheatMeal
                        FROM DailyReport
                        WHERE cheatMeal IS NOT NULL
                    """.trimIndent()
                    )

                    execSQL("""
                        INSERT INTO Note (id, date, note)
                        SELECT 
                            printf('%s_%d', 'note', date),  -- Generate unique IDs
                            date,
                            gymNote
                        FROM DailyReport
                        WHERE gymNote IS NOT NULL
                     """.trimIndent())

                    // 3. Create a temporary table without the removed columns
                    execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS DailyReport_new (
                            date INTEGER NOT NULL PRIMARY KEY,
                            performedWorkout INTEGER NOT NULL,
                            hadCreatine INTEGER NOT NULL,
                            hadCheatMeal INTEGER NOT NULL,
                            proteinGrams TEXT NOT NULL,
                            sleepQuality TEXT NOT NULL,
                            litersOfWater TEXT NOT NULL,
                            musclesTrained TEXT NOT NULL
                        )
                        """.trimIndent())

                    // 4. Copy remaining data from old table to new table
                    execSQL(
                        """
                            INSERT INTO DailyReport_new (date, performedWorkout, hadCreatine, hadCheatMeal, proteinGrams, sleepQuality, litersOfWater, musclesTrained)
                            SELECT date, performedWorkout, hadCreatine, hadCheatMeal, proteinGrams, sleepQuality, litersOfWater, musclesTrained
                            FROM DailyReport
                        """.trimIndent()
                    )

                    // 5. Remove the old DailyReport table
                    execSQL("DROP TABLE DailyReport")

                    // 6. Rename the new table to match the original table name
                    execSQL("ALTER TABLE DailyReport_new RENAME TO DailyReport")
                }

            }
        )
    }

}