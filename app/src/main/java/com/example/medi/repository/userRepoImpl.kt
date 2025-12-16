package com.example.medi.repository

import com.example.medi.model.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userRepoImpl: userRepo

{
    val auth : FirebaseAuth= FirebaseAuth.getInstance()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val ref: DatabaseReference=database.getReference("users")
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful){
                callback(true, "Login Successful")
            }else{
                callback(false, it.exception?.message.toString())
            }
        }

    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {
                callback(true,"Register Successful","${auth.currentUser?.uid}")
            } else {
                callback(false, it.exception?.message.toString(), "")
            }
        }
    }
    override fun addUserToDatabase(
        userId: String,
        userModel: userModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "User Added Successfully")
            } else {
                callback(false, "User Not Added")
            }
        }

    }



    override fun deleterAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "User Deleted Successfully")
            } else {
                callback(false, "User Not Deleted")
            }
        }
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Email Sent")
            } else {
                callback(false, "Email Not Sent")
            }
        }

    }

    override fun logOut(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true,"logout sucuesfull")
        }catch (e: Exception){
            callback(false,"${e.message}")
        }
    }



    override fun getAllUsers(callback: (Boolean, String, List<userModel?>) -> Unit) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                val users = mutableListOf<userModel>()
                for (data in snapshot.children){
                    val user = data.getValue(userModel::class.java)
                    if(user !=null){
                        users.add(user)
                    }
                }
                callback(true,"",users)
            }else{
                callback(false,"No Users Found",emptyList())
            }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,"${error.message}",emptyList())
            }
        })
    }

    override fun getUserById(
        userId: String,
        callback: (Boolean, String, userModel?) -> Unit
    ) {
        ref.child(userId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val user = snapshot.getValue(userModel::class.java)
                    callback(true,"User Found:",user)
                }else{
                    callback(false,"No User Found",null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,"${error.message}",null)
            }
        })

            }

    override fun updateUser(
        userId: String,
        userModel: userModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "User Updated Successfully")
            } else {
                callback(false, "User Not Updated")
            }
        }
    }




}