package com.minegksn.capstone.data.model.response

data class GetCategoriesResponse(
    val categories: List<String>?,
    val status: Int?,
    val message: String?
)
