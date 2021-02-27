package com.example.androiddevchallenge.model

import androidx.annotation.DrawableRes

data class Puppy(
    val id: Int,
    val name: String,
    val gender: Gender,
    val age: String,

    @DrawableRes
    val photo: Int
)

enum class Gender {
    MALE, FEMALE;

    override fun toString(): String {
        return if (this == MALE) "♂" else "♀"
    }
}