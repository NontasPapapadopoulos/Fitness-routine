package com.example.fitness_routine.data.cache

import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migrations {


    class Migrate4to5: AutoMigrationSpec

    companion object {
        val allMigrations = arrayOf<Migration>(
            object : Migration(4, 5) {
                override fun migrate(db: SupportSQLiteDatabase) = with(db) {
                    e//xecSQL()
                }

            }
        )
    }

}