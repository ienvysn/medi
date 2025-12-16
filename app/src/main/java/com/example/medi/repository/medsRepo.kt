package com.example.medi.repository

import com.example.medi.model.medsModel

interface medsRepo {
    fun addMeds(id: String,medsModel: medsModel,callback: (Boolean, String) -> Unit)
    fun updateMeds(id: String,medsModel: medsModel,callback: (Boolean, String) -> Unit)
    fun deleteMeds(id: String,callback: (Boolean, String) -> Unit)
    fun getMedsById(id: String,callback: (Boolean, String, medsModel?) -> Unit)
    fun getAllmeds(callback: (Boolean, String, List<medsModel?>) -> Unit)

}