package com.example.fitness_routine.domain.interactor.note

import com.example.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AddNoteTest {

    private lateinit var addNote: AddNote

    @Mock
    private lateinit var noteRepository: NoteRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        addNote = AddNote(noteRepository, dispatcher)
    }


    @Test
    fun execute_addNote() = runTest {
        whenever(noteRepository.put(any())).thenReturn(Unit)

        val result = addNote.execute(AddNote.Params(Date(), "note1"))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}