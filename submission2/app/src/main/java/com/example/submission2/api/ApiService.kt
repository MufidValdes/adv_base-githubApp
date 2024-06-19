package com.example.submission2.api


import com.example.submission2.BuildConfig
import com.example.submission2.models.DataUser
import com.example.submission2.models.DetailResponse
import com.example.submission2.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@GET("search/users")
	@Headers("Authorization: token "+ BuildConfig.GithubAPI)
	fun searchUser(
		@Query("q") login: String
	): Call<UserResponse>
//
	@GET("users/{login}/following")
	@Headers("Authorization: token "+ BuildConfig.GithubAPI)
	fun getUserFollowings(
		@Path("login") login: String
	): Call<List<DataUser>>

	@GET("users/{login}")
	@Headers("Authorization: token "+ BuildConfig.GithubAPI)
	fun getUserDetail(
		@Path("login") login: String
	): Call<DetailResponse>

	@GET("users/{login}/followers")
	@Headers("Authorization: token "+ BuildConfig.GithubAPI)
	fun getUserFollowers(
		@Path("login") login: String
	): Call<List<DataUser>>
}