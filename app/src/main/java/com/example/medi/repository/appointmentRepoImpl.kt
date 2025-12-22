package com.example.medi.repository

import com.example.medi.model.appointmentModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class appointmentRepoImpl: appointmentRepo {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference=database.getReference("appointment")


    override fun addAppointment(
        id: String,
        appointmentModel: appointmentModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id=ref.push().toString()

        ref.child(id).setValue(appointmentModel).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Appointment Added Successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun deleteAppointment(
        id: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(id).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Appointment Deleted Successfully")
            } else {
                callback(false, it.exception?.message.toString())
            }

        }
    }

    override fun updateAppointment(
        id: String,
        appointmentModel: appointmentModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(id).setValue(appointmentModel).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Appointment Updated Successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }

        }
    }

    override fun getAppointmentById(
        id: String,
        callback: (Boolean, String, appointmentModel?) -> Unit
    ) {
        ref.child(id).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val appointment = snapshot.getValue(appointmentModel::class.java)
                    callback(true, "Appointment Found:", appointment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, null)
            }


        })

    }

    override fun getAllAppointments(callback: (Boolean, String, List<appointmentModel>) -> Unit) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val appointments = mutableListOf<appointmentModel>()
                    for (data in snapshot.children) {
                        val appointment = data.getValue(appointmentModel::class.java)
                        if (appointment != null) {
                            appointments.add(appointment)
                        }
                    }
                    callback(true, "", appointments)
                } else {
                    callback(false, "No Appointments Found", emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, emptyList())
            }
        })

    }

}
