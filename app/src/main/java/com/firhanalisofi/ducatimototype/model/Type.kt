package com.firhanalisofi.ducatimototype.model

data class Type(
    val id: Long,
    val photoUrl: String,
    val name: String,
    val price: Int,
    val description: String,
    val color: String
)