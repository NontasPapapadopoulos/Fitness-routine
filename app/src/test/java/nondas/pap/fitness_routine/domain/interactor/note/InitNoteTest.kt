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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class InitNoteTest {

    private lateinit var initNote: InitNote

    @Mock
    private lateinit var noteRepository: NoteRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        initNote = InitNote(noteRepository, dispatcher)
    }


    @Test
    fun execute_initNote() = runTest {
        whenever(noteRepository.init(any())).thenReturn(Unit)

        val result = initNote.execute(InitNote.Params(date = 0))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}