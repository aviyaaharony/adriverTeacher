package com.example.aviyasapp

class Lesson {
    private var hour: String = ""
    private var date: String = ""
    private var isAvailable: Boolean = true
    private lateinit var student: StudentModel

    fun getHour(): String {
        return hour
    }

    fun setHour(value: String) {
        hour = value
    }

    fun getDate(): String {
        return date
    }

    fun setDate(value: String) {
        date = value
    }

    fun getIsAvailable(): Boolean {
        return isAvailable
    }

    fun setIsAvailable(value: Boolean) {
        isAvailable = value
    }

    fun getStudent(): StudentModel {
        return student
    }

    fun setName(value: StudentModel) {
        student = value
    }
}