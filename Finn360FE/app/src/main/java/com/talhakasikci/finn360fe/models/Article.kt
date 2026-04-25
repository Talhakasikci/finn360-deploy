package com.talhakasikci.finn360fe.models

data class Article(
    val userID: Int,
    val userName: String,
    val userSurname: String,
    val userEmail: String,
    val id: Int,
    val title: String,
    val body: String,
    val isFollowingUser: Boolean = false,
)
