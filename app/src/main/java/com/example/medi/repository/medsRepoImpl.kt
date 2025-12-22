package com.example.medi.repository

import com.example.medi.model.medsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class medsRepoImpl: medsRepo {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference=database.getReference("meds")

    override fun addMeds(
        id: String,
        medsModel: medsModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id=ref.push().toString()
        ref.child(id).setValue(medsModel).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Med Added Successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }

    }

    override fun updateMeds(
        id: String,
        medsModel: medsModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(id).setValue(medsModel).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Med Updated Successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun deleteMeds(
        id: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(id).removeValue().addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Med Deleted Successfully")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }

    }

    override fun getMedsById(
        id: String,
        callback: (Boolean, String, medsModel?) -> Unit
    ) {
        ref.child(id).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val meds = snapshot.getValue(medsModel::class.java)
                    callback(true,"Med Found:",meds)

                }else{
                    callback(false,"No Feds Found",null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,"${error.message}",null)

            }

        })

    }

    override fun getAllmeds(callback: (Boolean, String, List<medsModel>) -> Unit) {
       ref.addValueEventListener(object: ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()) {
                   val meds = mutableListOf<medsModel>()
                   for (data in snapshot.children) {
                       val med = data.getValue(medsModel::class.java)
                       if (med != null) {
                           meds.add(med)
                       }
                   }
                   callback(true, "", meds)
               } else {
                   callback(false, "No meds Found", emptyList())
               }
           }

           override fun onCancelled(error: DatabaseError) {
               callback(false, "${error.message}", emptyList())

           }
       })
       }
    }
