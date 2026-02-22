package com.example.medi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medi.model.appointmentModel
import com.example.medi.repository.appointmentRepo
import com.example.medi.viewModel.AppointmentViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AppointmentViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repo: appointmentRepo = mock()
    private val viewModel = AppointmentViewModel(repo)

    @Test
    fun addAppointment_success_test() {
        val appointment = appointmentModel(id = "1", doctorName = "Dr. Smith", date = "2024-02-22", time = "10:00 AM")

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Appointment Added Successfully")
            null
        }.`when`(repo).addAppointment(eq(appointment), any())

        var successResult = false
        var messageResult = ""

        viewModel.addAppointment(appointment) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Appointment Added Successfully", messageResult)
        verify(repo).addAppointment(eq(appointment), any())
    }

    @Test
    fun getAllAppointments_success_test() {
        val appointmentList = listOf(appointmentModel(id = "1", doctorName = "Dr. Smith"))

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<appointmentModel>) -> Unit>(0)
            callback(true, "Fetched", appointmentList)
            null
        }.`when`(repo).getAllAppointments(any())

        viewModel.getAllAppointments()

        assertEquals(appointmentList, viewModel.appointments.value)
        verify(repo).getAllAppointments(any())
    }
}
