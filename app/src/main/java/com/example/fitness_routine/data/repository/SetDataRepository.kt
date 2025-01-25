package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.repository.SetRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Date

class SetDataRepository(
    private val firestore: FirebaseFirestore
): SetRepository {


    companion object {
        private const val COLLECTION_NAME = "Sets"
    }

    override fun getSets(userId: String, date: Date): Flow<List<SetDomainEntity>> {
        return callbackFlow {
            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("date", date)
                .whereEqualTo("userId", userId)

            val listenerRegistration = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val sets = snapshot.toObjects(SetDomainEntity::class.java)
                    trySend(sets)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override fun getAllSets(userId: String): Flow<List<SetDomainEntity>> {
        return callbackFlow {
            val collectionRef = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)

            val listenerRegistration = collectionRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val sets = snapshot.toObjects(SetDomainEntity::class.java)
                    trySend(sets)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override suspend fun add(set: SetDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(set.id)
        docRef.set(set)
            .addOnSuccessListener { println("Successfully added set with id: ${set.id}") }
            .addOnFailureListener { e -> println("Failed to add set: ${e.message}") }
    }

    override suspend fun delete(set: SetDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(set.id)
        docRef.delete()
            .addOnSuccessListener { println("Successfully deleted set with id: ${set.id}") }
            .addOnFailureListener { e -> println("Failed to delete set: ${e.message}") }
    }

    override suspend fun update(set: SetDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(set.id)
        docRef.update(
            "repeats", set.repeats,
            "weight", set.weight,
        )
            .addOnSuccessListener { println("Successfully updated set with id: ${set.id}") }
            .addOnFailureListener { e -> println("Failed to update set: ${e.message}") }
    }

}