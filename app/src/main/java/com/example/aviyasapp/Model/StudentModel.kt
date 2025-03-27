package com.example.aviyasapp.Model

class StudentModel(email: String, isTeacher: Boolean) {
    private var name: String = ""
    private var state: Int = 0
    private var age: Double = 0.0
    private var lessons= mutableListOf<LessonModel>()
    private var email: String = ""
    private var isTeacher: Boolean = false

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

    fun getLessons(): List<LessonModel>{
        return lessons
    }

    fun setLessons(value: LessonModel) {
        lessons = mutableListOf(value)
    }
    fun getEmail(): String? {
        return email
    }

    fun setEmail(value: String) {
        email = value
    }
    fun getIsTeacher(): Boolean? {
        return isTeacher
    }

    fun setIsTeacher(value: Boolean) {
        isTeacher = value
    }

    fun addLesson(l: LessonModel) {
        this.lessons.add(l)
    }
    fun removeLesson(l: LessonModel){
        lessons.remove(l)
    }
    fun signToTeacher(t: TeacherModel){
        t.addStudent(this)
    }
}
