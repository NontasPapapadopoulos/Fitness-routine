package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.repository.WorkoutRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): WorkoutRepository {

    companion object {
        private const val COLLECTION_NAME = "Workouts"
    }

    override suspend fun addWorkout(workout: WorkoutDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(workout.id)

        // Set the workout data in Firestore, this will create or overwrite a document with the same id
        docRef.set(workout)
            .addOnSuccessListener { println("Workout successfully added for user: ${workout.userId}") }
            .addOnFailureListener { e -> println("Failed to add workout: ${e.message}") }
    }

    override suspend fun deleteWorkout(workout: WorkoutDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(workout.id)

        // Delete the workout document
        docRef.delete()
            .addOnSuccessListener { println("Workout successfully deleted for user: ${workout.userId}") }
            .addOnFailureListener { e -> println("Failed to delete workout: ${e.message}") }
    }

    override suspend fun updateWorkout(workout: WorkoutDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(workout.id)

        // Update the workout document in Firestore. You can selectively update fields here.
        docRef.update(
            "date", workout.date,
            "muscles", workout.muscles.map { it.toString() }
        )
            .addOnSuccessListener { println("Workout successfully updated for user: ${workout.userId}") }
            .addOnFailureListener { e -> println("Failed to update workout: ${e.message}") }
    }
}