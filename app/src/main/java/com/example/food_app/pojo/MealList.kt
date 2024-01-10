package com.example.food_app.pojo


import com.google.gson.annotations.SerializedName

data class MealList(
    @SerializedName("meals")
    val meals: List<Meal>?
)