package com.example.fitness_routine.domain.interactor.note

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.domain.repository.NoteRepository
import com.example.fitness_routine.domain.repository.SetRepository
import com.example.fitness_routine.note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetAllNotesTest {

    private lateinit var getAllNotes: GetAllNotes

    @Mock
    private lateinit var noteRepository: NoteRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getAllNotes = GetAllNotes(noteRepository, dispatcher)
    }


    @Test
    fun execute_getAllNotes() = runTest {
        whenever(noteRepository.getNotes()).thenReturn(flowOf(notes))

        val result = getAllNotes.execute(Unit).first()

        assertEquals(
            result,
            Result.success(notes)
        )
    }

    companion object {
        val notes = listOf(DummyEntities.note)
    }
}