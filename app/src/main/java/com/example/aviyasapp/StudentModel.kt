package com.example.aviyasapp

class StudentModel {
    private var name: String? = null
    private var state: Int = 0
    private var age: Double = 0.0
    private var lessons= Array<Lesson>()

    fun getName(): String? {
        return name
    }

    fun setName(value: String) {
        name = value
    }

    fun getState(): Int {
        return state
    }

    fun setState(value: Int) {
        state = value
    }

    fun getAge(): Double {
        return age
    }

    fun setAge(value: Double) {
        age = value
    }

    fun getLessons(): Array<Lesson> {
        return lessons
    }

    fun setLessons(value: Lesson) {
        lessons = arrayOf(value)
    }

}
