package com.example.aviyasapp

class TeacherModel {
    private var name: String? = null
    private var students = ArrayList<StudentModel>()
    private var hourOfWork = ArrayList<String>()
    private var price: Int = 0

    fun getName(): String {
        return name
    }

    fun setName(value: String) {
        name = value
    }

    fun getStudents(): ArrayList<StudentModel> {
        return students
    }

    fun setStudents(value: ArrayList<StudentModel>) {
        students = value
    }

    fun getHourOfWork(): ArrayList<String> {
        return hourOfWork
    }

    fun setHourOfWork(value: ArrayList<String>) {
        hourOfWork = value
    }

    fun getPrice(): Int {
        return price
    }

    fun setPrice(value: Int) {
        price = value
    }

    fun addStudent(s: StudentModel){
        students.add(s)
    }

    fun removeStudent(s: StudentModel){
        for (i 1..)
    }
}
