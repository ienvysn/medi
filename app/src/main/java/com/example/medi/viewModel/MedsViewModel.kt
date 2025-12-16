package com.example.medi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medi.model.medsModel
import com.example.medi.repository.medsRepo



class MedsViewModel (val repo: medsRepo): ViewModel(){

    fun addMeds(id: String,medsModel: medsModel,callback: (Boolean, String) -> Unit){
        repo.addMeds(id,medsModel,callback)
    }

    fun updateMeds(id: String,medsModel: medsModel,callback: (Boolean, String) -> Unit){
        repo.updateMeds(id,medsModel,callback)
    }
    fun deleteMeds(id: String,callback: (Boolean, String) -> Unit){
        repo.deleteMeds(id,callback)
    }

    private val _allmeds = MutableLiveData<List<medsModel?>>()

    val meds: MutableLiveData<List<medsModel?>>
        get() = _allmeds

    private val _meds = MutableLiveData<medsModel?>()

    val med: MutableLiveData<medsModel?>
        get() = _meds




    fun getMedsById(id: String){
        repo.getMedsById(id){
            success, message, data ->
            if (success){
                _meds.postValue(data)
            }else{
                _meds.postValue(data)
            }
        }


    }
    fun getAllmeds(){
        repo.getAllmeds(){

            success, message, data ->
            if (success){
                _allmeds.postValue(data)
            }else{
                _allmeds.postValue(data)
            }
        }
    }
}