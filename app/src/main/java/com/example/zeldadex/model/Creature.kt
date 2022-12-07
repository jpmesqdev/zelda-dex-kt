package com.example.zeldadex.model

data class Creature(
    val id: Int,
    val name: String,
    val desc: String,
    val drops: List<String>,
    val loc: List<String>,
    val img: String,
) {
}