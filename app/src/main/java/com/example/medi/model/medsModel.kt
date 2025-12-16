package com.example.medi.model


data class medsModel(
    var id: String = "",
    val name: String = "",
    val dosage: String = "",
    val schedule: String = "",
    val frequency: String = "",
    val notes: String = "",
    val status: String = ""
){
    fun toMap(): Map<String,Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "dosage" to dosage,
            "schedule" to schedule,
            "frequency" to frequency,
            "notes" to notes,
            "status" to status
        )
    }
}


