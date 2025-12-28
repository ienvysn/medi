package com.example.medi.model

data class appointmentModel(
    var id: String = "",
    val doctorName: String = "",
    val specialty: String? = null,
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val notes: String? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "doctorName" to doctorName,
            "specialty" to specialty,
            "date" to date,
            "time" to time,
            "location" to location,
            "notes" to notes
        )
    }
}