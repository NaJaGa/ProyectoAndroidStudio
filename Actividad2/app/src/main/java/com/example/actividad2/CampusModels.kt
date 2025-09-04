package com.example.actividad2

data class CampusesResponse(
    val Campuses: List<Campus>
)

data class Campus(
    val CampusID: Int,
    val CampusName: String,
    val IsActive: Boolean
)

// DATA CLASS PARA EL POST LOGIN REQUEST
data class LoginRequest(
    val LoginData: LoginData
)

data class LoginData(
    val Email: String,
    val Password: String
)

data class LoginResponse(
    val d: UserData
)

data class UserData(
    val UserLogged: List<User> = emptyList()
)

data class User(
    val UserId: Int,
    val Name: String,
    val LastName: String,
    val Email: String,
    val StudentNumber: Int,
    val CampusID: Int,
    val Campus: String
)

data class NewUserRequest(
    val NewUser: NewUser
)

data class NewUser(
    val Name: String,
    val LastName: String,
    val Email: String,
    val Password: String,
    val StudentNumber: Int,
    val CampusID: Int
)

data class NewUserResponse(
    val d: ExecuteResult
)

data class ExecuteResult(
    val ExecuteResult: String,
    val Message: String? = null
)



