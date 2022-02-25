package com.example.shoppinglist.model

data class Shopp(
    val title: String,
    val description: String,
    val id: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shopp

        if (id != other.id) return false

        return true

    }

    override fun hashCode(): Int {
        return id
    }
}