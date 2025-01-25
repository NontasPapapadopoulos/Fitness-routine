package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.repository.NoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import java.util.Date
import java.util.UUID
import javax.inject.Inject

class NoteDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : NoteRepository {

    companion object {
        private const val COLLECTION_NAME = "Notes"
    }

    override fun getNotes(userId: String, date: Date): Flow<List<NoteDomainEntity>> {
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
                    val notes = snapshot.toObjects(NoteDomainEntity::class.java)
                    trySend(notes)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override fun getNotes(userId: String): Flow<List<NoteDomainEntity>> {
        return callbackFlow {
            val collectionRef = firestore.collection(COLLECTION_NAME)

            val listenerRegistration = collectionRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val notes = snapshot.toObjects(NoteDomainEntity::class.java)
                    trySend(notes)
                } else {
                    trySend(emptyList())
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override suspend fun put(note: NoteDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(note.id)
        docRef.set(note)
            .addOnSuccessListener { println("Successfully added note with id: ${note.id}") }
            .addOnFailureListener { e -> println("Failed to add note: ${e.message}") }
    }

    override suspend fun delete(note: NoteDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(note.id)
        docRef.delete()
            .addOnSuccessListener { println("Successfully deleted note with id: ${note.id}") }
            .addOnFailureListener { e -> println("Failed to delete note: ${e.message}") }
    }

    override suspend fun update(note: NoteDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(note.id)
        docRef.update(
            "note", note.text,
            "date", note.date
        )
            .addOnSuccessListener { println("Successfully updated note with id: ${note.id}") }
            .addOnFailureListener { e -> println("Failed to update note: ${e.message}") }
    }

    override suspend fun init(userId: String, date: Date) {
        val id = UUID.randomUUID().toString()
        val defaultNote = NoteDomainEntity(
            id = id,
            userId = userId,
            date = date,
            text = ""
        )
        put(defaultNote)
    }
}