package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.repository.CheatMealRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import java.util.Date
import java.util.UUID
import javax.inject.Inject

class CheatMealDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): CheatMealRepository {
    companion object {
        private const val COLLECTION_NAME = "CheatMeals"
    }

    override fun getCheatMeals(userId: String): Flow<List<CheatMealDomainEntity>> {
        return callbackFlow {
            val collectionRef = firestore.collection(COLLECTION_NAME)

            val listenerRegistration = collectionRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val cheatMeals = snapshot.toObjects(CheatMealDomainEntity::class.java)
                    trySend(cheatMeals)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    // Fetch cheat meals for a specific date
    override fun getCheatMeals(userId: String, date: Date): Flow<List<CheatMealDomainEntity>> {
        return callbackFlow {
            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .whereEqualTo("date", date)

            val listenerRegistration = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // Close flow on error
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val cheatMeals = snapshot.toObjects(CheatMealDomainEntity::class.java)
                    trySend(cheatMeals)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override suspend fun put(cheatMeal: CheatMealDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(cheatMeal.id)
        docRef.set(cheatMeal.toData())
            .addOnSuccessListener { println("Successfully added/updated cheat meal with id: ${cheatMeal.id}") }
            .addOnFailureListener { e -> println("Failed to add/update cheat meal: ${e.message}") }
    }

    override suspend fun delete(cheatMeal: CheatMealDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(cheatMeal.id)
        docRef.delete()
            .addOnSuccessListener { println("Successfully deleted cheat meal with id: ${cheatMeal.id}") }
            .addOnFailureListener { e -> println("Failed to delete cheat meal: ${e.message}") }
    }

    override suspend fun init(userId: String, date: Date) {
        val id = UUID.randomUUID().toString()
        val defaultCheatMeal = CheatMealDomainEntity(
            id = id,
            userId = userId,
            date = date,
            text = "Default Cheat Meal"
        )
        put(defaultCheatMeal)
    }

}