package com.example.aviyasapp.Model

class StudentModel {
    private var name: String? = null
    private var state: Int = 0
    private var age: Double = 0.0
    private var lessons= mutableListOf<Lesson>()

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

    fun getLessons(): List<Lesson>{
        return lessons
    }

    fun setLessons(value: Lesson) {
        lessons = mutableListOf(value)
    }

    fun addLesson(l: Lesson) {
        this.lessons.add(l)
    }
    fun removeLesson(l: Lesson){
        lessons.remove(l)
    }
    fun signToTeacher(t: TeacherModel){
        t.addStudent(this)
    }
}
