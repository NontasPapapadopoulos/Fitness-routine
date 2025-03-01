package nondas.pap.fitness_routine.domain.interactor.note

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.domain.repository.NoteRepository
import nondas.pap.fitness_routine.note
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
class GetNotesTest {

    private lateinit var getNotes: GetNotes

    @Mock
    private lateinit var noteRepository: NoteRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getNotes = GetNotes(noteRepository, dispatcher)
    }


    @Test
    fun execute_getNotes() = runTest {
        whenever(noteRepository.getNotes(any())).thenReturn(flowOf(notes))

        val result = getNotes.execute(GetNotes.Params(0)).first()

        assertEquals(
            result,
            Result.success(notes)
        )
    }

    companion object {
        val notes = listOf(DummyEntities.note)
    }
}