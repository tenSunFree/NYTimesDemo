package com.home.nytimesdemo.list.model

import java.io.Serializable

data class ListResponse(
    val list: MutableList<ListResponseImage> = mutableListOf()
) : Serializable

data class ListResponseImage(
    val name: String = "",
    val url: String = ""
) : Serializable