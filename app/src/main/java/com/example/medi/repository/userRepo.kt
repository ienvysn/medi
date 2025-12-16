package com.example.medi.repository

import com.example.medi.model.userModel

interface userRepo {
    fun login(email: String, password: String,callback: (Boolean, String) -> Unit)
    fun register(email: String, password: String,callback: (Boolean, String, String) -> Unit)
    fun deleterAccount(userId: String,callback: (Boolean, String) -> Unit)
    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit)
    fun logOut(callback: (Boolean, String) -> Unit)
    fun getAllUsers(callback: (Boolean, String, List<userModel?>) -> Unit)
    fun getUserById(userId: String,callback: (Boolean, String, userModel?) -> Unit)
    fun updateUser(userId: String,userModel: userModel,callback: (Boolean, String) -> Unit)
    fun addUserToDatabase(userId: String,userModel: userModel,callback: (Boolean, String) -> Unit)





}


