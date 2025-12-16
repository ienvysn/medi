package com.example.medi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medi.model.userModel
import com.example.medi.repository.userRepo

class UserViewModel(val repo: userRepo) : ViewModel() {
    fun login(email: String, password: String,callback: (Boolean, String) -> Unit){
        repo.login(email,password,callback)
    }
    fun register(email: String, password: String,callback: (Boolean, String, String) -> Unit){
        repo.register(email,password,callback)
    }
    fun deleterAccount(userId: String,callback: (Boolean, String) -> Unit){

        repo.deleterAccount(userId,callback)
    }
    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }
    fun logOut(callback: (Boolean, String) -> Unit){

        repo.logOut(callback)
    }

    fun updateUser(userId: String,userModel: userModel,callback: (Boolean, String) -> Unit){
        repo.updateUser(userId,userModel,callback)
    }
    fun addUserToDatabase(userId: String,userModel: userModel,callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId,userModel,callback)
    }


    private  val _users = MutableLiveData<List<userModel?>>()
    val users: MutableLiveData<List<userModel?>>
        get() = _users



    private val _user = MutableLiveData<userModel?>()
    val user: MutableLiveData<userModel?>
        get() = _user


    private val _loading= MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean>
        get() = _loading




    fun getAllUsers(){
        repo.getAllUsers(){
            success, message, data ->
            if (success){
                _loading.postValue(false)
                _users.postValue(data)
            }else{
                _loading.postValue(false)
                _users.postValue(data)
            }
        }
    }
    fun getUserById(userId: String){
        repo.getUserById(userId){
            success, message, user ->
            if (success){
                _loading.postValue(false)
                _user.postValue(user)
            }else{
                _loading.postValue(false)
                _user.postValue(user)
            }
        }

    }

}