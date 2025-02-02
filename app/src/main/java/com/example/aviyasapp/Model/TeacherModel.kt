package com.example.aviyasapp.Model

class TeacherModel {
    private var name: String =""
    private var students = ArrayList<StudentModel>()
    private var hourOfWork = ArrayList<String>()
    private var price: Int = 0
    private var lessons = ArrayList<LessonModel>()

    fun getLessons(): ArrayList<LessonModel> {
        return lessons
    }

    fun setLessons(value: ArrayList<LessonModel>) {
        lessons = value
    }

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

