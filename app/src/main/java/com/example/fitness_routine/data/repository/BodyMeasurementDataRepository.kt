package com.example.fitness_routine.data.repository


import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import java.util.Date

class BodyMeasurementDataRepository(
    private val firestore: FirebaseFirestore
): BodyMeasurementRepository {


    companion object {
        private const val COLLECTION_NAME = "BodyMeasurements"
    }

    override fun getBodyMeasurement(userId: String, date: Date): Flow<BodyMeasurementDomainEntity?> {
        return callbackFlow {
            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .whereEqualTo("date", date)

            query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val measurement = snapshot.documents.firstOrNull()?.toObject(BodyMeasurementDomainEntity::class.java)
                    trySend(measurement)
                } else {
                    trySend(null)
                }
            }
            awaitClose { }
        }
    }

    // Check if body measurement exists for a specific date
    override fun hasBodyMeasurement(userId: String, date: Date): Flow<Boolean> {
        return callbackFlow {

            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .whereEqualTo("date", date)

            query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                trySend(snapshot != null && !snapshot.isEmpty)
            }
            awaitClose { }
        }
    }

    override fun getBodyMeasurements(userId: String): Flow<List<BodyMeasurementDomainEntity>> {
        return callbackFlow {

            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)

            query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val measurements = snapshot.documents.mapNotNull { it.toObject(BodyMeasurementDomainEntity::class.java) }
                    trySend(measurements)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { }
        }
    }

    override suspend fun put(measurement: BodyMeasurementDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(measurement.id)

        docRef.set(measurement)
            .addOnSuccessListener { println("Body measurement successfully added for user: ${measurement.userId}") }
            .addOnFailureListener { e -> println("Failed to add body measurement: ${e.message}") }
    }

    override suspend fun update(measurement: BodyMeasurementDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(measurement.id)

        docRef.update(
            "weight", measurement.weight,
            "fat", measurement.fat,
            "muscleMass", measurement.muscleMass,
            "bmi", measurement.bmi,
            "tbw", measurement.tbw,
            "bmr", measurement.bmr,
            "visceralFat", measurement.visceralFat,
            "metabolicAge", measurement.metabolicAge
        )
            .addOnSuccessListener { println("Body measurement successfully updated for user: ${measurement.userId}") }
            .addOnFailureListener { e -> println("Failed to update body measurement: ${e.message}") }
    }

    override suspend fun delete(measurement: BodyMeasurementDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(measurement.id)

        docRef.delete()
            .addOnSuccessListener { println("Body measurement successfully deleted for user: ${measurement.userId}") }
            .addOnFailureListener { e -> println("Failed to delete body measurement: ${e.message}") }
    }

}


