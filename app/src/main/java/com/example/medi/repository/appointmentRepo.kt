package com.example.medi.repository

import com.example.medi.model.appointmentModel

interface appointmentRepo {
    fun addAppointment(id: String, appointmentModel: appointmentModel, callback: (Boolean, String) -> Unit)
    fun deleteAppointment(id: String, callback: (Boolean, String) -> Unit)
    fun updateAppointment(id: String, appointmentModel: appointmentModel, callback: (Boolean, String) -> Unit)
    fun getAppointmentById(id: String, callback: (Boolean, String, appointmentModel?) -> Unit)
    fun getAllAppointments(callback: (Boolean, String, List<appointmentModel>) -> Unit)


}