data class StudentModel(
    var email: String = "",
    var isTeacher: Boolean = false,
    var name: String = "",
    var phone: String = "",
    var uid: String = ""
) {
    // Constructor ריק חובה לפיירסטור
    constructor() : this("",  false, "", "", "")
}