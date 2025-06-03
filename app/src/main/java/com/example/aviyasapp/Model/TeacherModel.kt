package com.example.aviyasapp.Model

import StudentModel

data class TeacherModel(
    var name: String = "",
    var uid: String = "",
    var email: String = "",
    var price: Int = 0,
    var location: String = "",
    var students: ArrayList<StudentModel> = ArrayList(),
    var hourOfWork: ArrayList<String> = ArrayList(),
    var lessons: MutableList<LessonModel> = ArrayList()
) {
    // Explicit no-argument constructor for Firebase
    constructor() : this("", "","", 0, "", ArrayList(), ArrayList(), ArrayList())

    // פונקציות להוספה והסרה של תלמידים ושיעורים
    fun addStudent(s: StudentModel) {
        students.add(s)
    }

    fun removeStudent(s: StudentModel) {
        students.remove(s)
    }

    fun removeLesson(l: LessonModel) {
        lessons.remove(l)
    }

    fun addHourOfWork(hour: String) {
        hourOfWork.add(hour)
    }

    fun removeHourOfWork(hour: String) {
        hourOfWork.remove(hour)
    }
}