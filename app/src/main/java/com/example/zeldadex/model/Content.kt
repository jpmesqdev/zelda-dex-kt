package com.example.zeldadex.model

data class Content(
    val category: String,
    val commonLoc: List<String>,
    val description: String,
    val drops: List<String>,
    val id: Int,
    val image: String,
    val name: String,
) {
}