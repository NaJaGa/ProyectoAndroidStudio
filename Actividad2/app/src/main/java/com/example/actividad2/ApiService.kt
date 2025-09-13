package com.example.actividad2

import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @GET("campuses")
    suspend fun getCampuses(): CampusesResponse

    @POST("UserLogin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("UserUI")
    suspend fun signup(@Body request: NewUserRequest ): NewUserResponse

    @POST("Friends")
    suspend fun findFriends(@Body request: FriendsRequest ): FriendsResponse

    @POST("FriendsUI")
    suspend fun saveFriends(@Body request: SaveFriendsRequest ): NewUserResponse

}