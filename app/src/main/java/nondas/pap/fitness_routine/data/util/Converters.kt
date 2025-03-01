package nondas.pap.fitness_routine.data.util

import androidx.room.TypeConverter
import nondas.pap.fitness_routine.domain.entity.enums.Muscle

class Converters {
    @TypeConverter
    fun fromMuscleList(muscles: List<Muscle>): String {
        return muscles.joinToString(separator = ",") { it.name }
    }

    @TypeConverter
    fun toMuscleList(muscleString: String): List<Muscle> {
        return muscleString.split(",").map { Muscle.valueOf(it) }
    }

}