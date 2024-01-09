package com.example.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.food_app.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal:Meal)
    @Delete
    suspend fun deleteMeal(meal:Meal)
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() :List<Meal>

}