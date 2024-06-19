package com.example.submission3.favorite.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUser(
	@PrimaryKey
	@ColumnInfo(name = "id")
	var id: Int,

	@ColumnInfo(name = "login")
	var login: String?,

	@ColumnInfo(name = "html_url")
	var htmlUrl: String? = null,

	@ColumnInfo(name = "avatar_url")
	var avatarUrl: String? = null,
)
