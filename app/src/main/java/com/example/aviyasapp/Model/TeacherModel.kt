package com.example.aviyasapp.Model

import android.provider.ContactsContract.CommonDataKinds.Email

data class TeacherModel(
    var name: String = "",
    var email: String = "",
    var price: Int = 0,
    var location :String = "",
    var students: ArrayList<StudentModel> = ArrayList(),
    var hourOfWork: ArrayList<String> = ArrayList(),
    var lessons: ArrayList<LessonModel> = ArrayList(),


) {

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
