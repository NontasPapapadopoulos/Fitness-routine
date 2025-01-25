package com.example.fitness_routine.data.repository


import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.repository.CardioRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class CardioDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): CardioRepository {

    companion object {
        private const val COLLECTION_NAME = "Cardios"
    }

    // Fetch all cardio records for a specific date
    override fun getCardios(userId: String, date: Date): Flow<List<CardioDomainEntity>> {
        return callbackFlow {
            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .whereEqualTo("date", date)

            query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // Close flow on error
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val cardios = snapshot.documents.mapNotNull { it.toObject(CardioDomainEntity::class.java) }
                    trySend(cardios)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { }
        }
    }

    override fun getCardios(userId: String): Flow<List<CardioDomainEntity>> {
        return callbackFlow {

            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)

            query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val cardios = snapshot.documents.mapNotNull { it.toObject(CardioDomainEntity::class.java) }
                    trySend(cardios)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { }
        }
    }

    override suspend fun put(cardio: CardioDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(cardio.id)

        docRef.set(cardio)
            .addOnSuccessListener { println("Cardio successfully added for user: ${cardio.userId}") }
            .addOnFailureListener { e -> println("Failed to add cardio: ${e.message}") }
    }

    override suspend fun update(cardio: CardioDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(cardio.id)

        docRef.update(
            "type", cardio.type,
            "minutes", cardio.minutes
        )
            .addOnSuccessListener { println("Cardio successfully updated for user: ${cardio.userId}") }
            .addOnFailureListener { e -> println("Failed to update cardio: ${e.message}") }
    }

    override suspend fun delete(cardio: CardioDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(cardio.id)

        docRef.delete()
            .addOnSuccessListener { println("Cardio successfully deleted for user: ${cardio.userId}") }
            .addOnFailureListener { e -> println("Failed to delete cardio: ${e.message}") }
    }

    override suspend fun init(userId: String, date: Date) {

        val defaultCardio = CardioDomainEntity(
            id = UUID.randomUUID().toString(),
            userId = userId,
            date = date,
            type = "Running",
            minutes = "30"
        )

        val docRef = firestore.collection(COLLECTION_NAME).document(defaultCardio.id)

        // Check if the cardio record already exists for the user on this date
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) {
            docRef.set(defaultCardio)
                .addOnSuccessListener { println("Default cardio initialized for user: $userId") }
                .addOnFailureListener { e -> println("Failed to initialize default cardio: ${e.message}") }
        }
    }


}