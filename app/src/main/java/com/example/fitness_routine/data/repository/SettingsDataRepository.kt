package com.example.fitness_routine.data.repository


import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.repository.SettingsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class SettingsDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): SettingsRepository {

    companion object {
        private const val COLLECTION_NAME = "Settings"
    }

    override fun getSettings(userId: String): Flow<SettingsDomainEntity?> {
        return callbackFlow {
            val docRef = firestore.collection(COLLECTION_NAME).document(userId)

            val listenerRegistration = docRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // Close flow on error
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val settings = snapshot.toObject(SettingsDomainEntity::class.java)
                    trySend(settings)
                } else {
                    trySend(null)
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }


    override suspend fun changeSettings(userId: String, settings: SettingsDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(userId)

        docRef.set(settings)
            .addOnSuccessListener { println("Settings successfully updated for user: ${settings.userId}") }
            .addOnFailureListener { e -> println("Failed to update settings: ${e.message}") }
    }

    // Change the user's choice in the settings
    override suspend fun changeChoice(userId: String, choice: Choice) {
        val docRef = firestore.collection(COLLECTION_NAME).document(userId)


        docRef.update("choice", choice.toString())
            .addOnSuccessListener { println("Choice successfully updated for user: $userId") }
            .addOnFailureListener { e -> println("Failed to update choice: ${e.message}") }
    }

    override suspend fun init(userId: String) {
        val defaultSettings = SettingsDomainEntity(
            id = UUID.randomUUID().toString(),
            userId = userId,
            choice = "Default Choice",
            isDarkModeEnabled = false,
            breakDuration = "30 minutes"
        )

        val docRef = firestore.collection(COLLECTION_NAME).document(userId)

        // Check if the settings already exist before initializing them
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) {
            docRef.set(defaultSettings)
                .addOnSuccessListener { println("Default settings initialized for user: $userId") }
                .addOnFailureListener { e -> println("Failed to initialize default settings: ${e.message}") }
        }
    }

}