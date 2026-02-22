package com.example.medi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medi.model.medsModel
import com.example.medi.repository.medsRepo
import com.example.medi.viewModel.MedsViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MedsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repo: medsRepo = mock()
    private val viewModel = MedsViewModel(repo)

    @Test
    fun addMeds_success_test() {
        val medicine = medsModel(id = "1", name = "Paracetamol", dosage = "500mg")

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Medicine Added Successfully")
            null
        }.`when`(repo).addMeds(eq(medicine), any())

        var successResult = false
        var messageResult = ""

        viewModel.addMeds(medicine) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Medicine Added Successfully", messageResult)
        verify(repo).addMeds(eq(medicine), any())
    }

    @Test
    fun getAllMeds_success_test() {
        val medsList = listOf(medsModel(id = "1", name = "Paracetamol"))

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<medsModel>) -> Unit>(0)
            callback(true, "Fetched", medsList)
            null
        }.`when`(repo).getAllmeds(any())

        viewModel.getAllmeds()

        assertEquals(medsList, viewModel.allmeds.value)
        verify(repo).getAllmeds(any())
    }
}
