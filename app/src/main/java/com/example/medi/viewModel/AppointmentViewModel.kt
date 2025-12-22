package com.example.medi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medi.model.appointmentModel
import com.example.medi.repository.appointmentRepo



class AppointmentViewModel (val repo: appointmentRepo): ViewModel(){

    fun addAppointment(id: String, appointmentModel: appointmentModel, callback: (Boolean, String) -> Unit){

        repo.addAppointment(id,appointmentModel,callback)
    }
    fun deleteAppointment(id: String, callback: (Boolean, String) -> Unit){

        repo.deleteAppointment(id,callback)
    }
    fun updateAppointment(id: String, appointmentModel: appointmentModel, callback: (Boolean, String) -> Unit){

        repo.updateAppointment(id,appointmentModel,callback)
    }



    private val _appointments= MutableLiveData<List<appointmentModel>>()
    private val _singleAppointment = MutableLiveData<appointmentModel?>()

    val appointments: LiveData<List<appointmentModel>>
        get() = _appointments

    val singleAppointment: LiveData<appointmentModel?>
        get() = _singleAppointment

    fun getAppointmentById(id: String, callback: (Boolean, String, appointmentModel?) -> Unit){

        repo.getAppointmentById(id){
            success, message, data ->
            if (success){
                _singleAppointment.postValue(data)
            }else{
                _singleAppointment.postValue(data)
            }
        }
    }

    fun getAllAppointments(callback: (Boolean, String, List<appointmentModel>) -> Unit){

        repo.getAllAppointments{
            success, message, data ->
            if (success){
                _appointments.postValue(data)
            }else{
                _appointments.postValue(emptyList())
            }
        }
    }
    }
}