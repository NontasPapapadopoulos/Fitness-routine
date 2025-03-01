package com.example.fitness_routine.domain.interactor.note

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.domain.repository.NoteRepository
import com.example.fitness_routine.note
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
class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote

    @Mock
    private lateinit var noteRepository: NoteRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        deleteNote = DeleteNote(noteRepository, dispatcher)
    }


    @Test
    fun execute_addNote() = runTest {
        whenever(noteRepository.delete(any())).thenReturn(Unit)

        val result = deleteNote.execute(DeleteNote.Params(DummyEntities.note))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}