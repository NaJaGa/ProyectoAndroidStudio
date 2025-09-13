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
    val CampusName: String
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


data class FriendsRequest(
    val FriendsFilter: FriendsRequestData
)

data class FriendsRequestData(
    val LoggedUserID : Int,
    val CampusID : Int,
    val Name : String
)

data class FriendsResponse(
    val d: FriendsResponseData
)

data class FriendsResponseData(
    val ExecuteResult: String,
    val Message: String? = null,
    val Friends: List<FriendsData>
)


data class FriendsData(
    val UserID: Int,
    val CompleteName: String,
    val Email: String,
    val StudentNumber: Int,
    val CampusID: Int,
    val CampusName: String,
    val IsFriend: Boolean
)

data class SaveFriendsRequest(
    val FriendsList: SaveFriendsData
)

data class SaveFriendsData(
    val LoggedUserID: Int,
    val Friends: String? = null
)



