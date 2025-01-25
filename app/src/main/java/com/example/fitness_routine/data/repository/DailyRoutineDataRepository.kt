package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.UUID

class DailyRoutineDataRepository(
    private val firestore: FirebaseFirestore
): DailyRoutineRepository {

    companion object {
        private const val COLLECTION_NAME = "DailyReports"
    }

    override fun getDailyReports(userId: String): Flow<List<DailyReportDomainEntity>> {
        return callbackFlow {
            val collectionRef = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
            val listenerRegistration = collectionRef
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error) // Close the flow with the error
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val reports = snapshot.toObjects(DailyReportDataEntity::class.java)
                            .map { it.toDomain() }
                        trySend(reports) // Emit the fetched data
                    } else {
                        trySend(emptyList())
                    }
                }
            awaitClose { listenerRegistration.remove() }
        }
    }



    override fun getDailyReport(userId: String, date: Date): Flow<DailyReportDomainEntity> {
        return callbackFlow {
            val query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .whereEqualTo("date", date)

            val listenerRegistration = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val report = snapshot.documents[0].toObject(DailyReportDataEntity::class.java)?.toDomain()
                    report?.let { trySend(it) } ?: close(NullPointerException("Report not found"))
                } else {
                    close(NullPointerException("Report not found"))
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
    }

    override suspend fun update(report: DailyReportDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(report.id)
        docRef.set(report.toData())
            .addOnSuccessListener { println("Successfully updated report with id: ${report.id}") }
            .addOnFailureListener { e -> println("Failed to update report: ${e.message}") }
    }

    override suspend fun delete(report: DailyReportDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(report.id)
        docRef.delete()
            .addOnSuccessListener { println("Successfully deleted report with id: ${report.id}") }
            .addOnFailureListener { e -> println("Failed to delete report: ${e.message}") }
    }

    override suspend fun put(report: DailyReportDomainEntity) {
        val docRef = firestore.collection(COLLECTION_NAME).document(report.id)
        docRef.set(report.toData())
            .addOnSuccessListener { println("Successfully inserted report with id: ${report.id}") }
            .addOnFailureListener { e -> println("Failed to insert report: ${e.message}") }
    }

    override suspend fun initDailyReport(userId: String, date: Date) {
        val id = UUID.randomUUID().toString()
        val defaultReport = DailyReportDomainEntity(
            id = id,
            userId = userId,
            date = date,
            performedWorkout = false,
            hadCreatine = false,
            hadCheatMeal = false,
            proteinGrams = "",
            sleepQuality = "",
            litersOfWater = "",
            musclesTrained = emptyList()
        )
        put(defaultReport)
    }

}