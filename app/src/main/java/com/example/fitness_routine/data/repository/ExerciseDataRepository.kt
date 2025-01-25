package com.example.fitness_routine.data.repository

import androidx.datastore.dataStore
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.repository.ExerciseRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): ExerciseRepository {

    companion object {
        private const val COLLECTION_NAME = "Exercises"
    }


    override fun getExercises(userId: String): Flow<List<ExerciseDomainEntity>> {
        return callbackFlow {
            val collectionRef = firestore.collection(COLLECTION_NAME)

            val listenerRegistration = collectionRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val exercises = snapshot.toObjects(ExerciseDomainEntity::class.java)
                    trySend(exercises)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override suspend fun add(exercise: ExerciseDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(exercise.id)
        docRef.set(exercise)
            .addOnSuccessListener { println("Successfully added exercise with id: ${exercise.id}") }
            .addOnFailureListener { e -> println("Failed to add exercise: ${e.message}") }
    }

    override suspend fun edit(oldName: String, newName: String) {
        val query = firestore.collection(COLLECTION_NAME)
            .whereEqualTo("name", oldName)

        query.get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot.documents) {
                    doc.reference.update("name", newName)
                        .addOnSuccessListener { println("Successfully updated exercise name to: $newName") }
                        .addOnFailureListener { e -> println("Failed to update exercise name: ${e.message}") }
                }
            }
            .addOnFailureListener { e -> println("Failed to find exercise with name $oldName: ${e.message}") }
    }

    override suspend fun delete(exercise: ExerciseDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(exercise.id)
        docRef.delete()
            .addOnSuccessListener { println("Successfully deleted exercise with id: ${exercise.id}") }
            .addOnFailureListener { e -> println("Failed to delete exercise: ${e.message}") }
    }
}