package com.example.aviyasapp.Model

import StudentModel

data class LessonModel(
    val hour     : String = "",
    val date     : String = "",   // "01", "15" וכו'
    val isAvailable: Boolean = false,
    val student  : StudentModel = StudentModel()
) {

}
