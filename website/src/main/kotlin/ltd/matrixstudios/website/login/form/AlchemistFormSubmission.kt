package ltd.matrixstudios.website.login.form

data class AlchemistFormSubmission(
    val username: String = "Guest",
    val password: String? = null,
    val secret: String? = null
)
