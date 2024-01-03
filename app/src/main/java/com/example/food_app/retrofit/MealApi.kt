package com.example.food_app.retrofit

import com.example.food_app.pojo.CategoryList
import com.example.food_app.pojo.MealsByCategoryList
import com.example.food_app.pojo.MealList
import com.example.food_app.pojo.MealsByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
    @GET("lookup.php?")
    fun getMealDetails( @Query("i") id:String ) : Call<MealList>
    @GET("filter.php?")
    fun getPopularItem( @Query("c") categoryName:String ) : Call<MealsByCategoryList>
    @GET("categories.php")
    fun getCategories() : Call<CategoryList>
    @GET("filter.php")
    fun getMealsByCategory( @Query("c") categoryName: String):Call<MealsByCategoryList>
}