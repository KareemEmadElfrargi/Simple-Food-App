package com.example.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_app.pojo.Category
import com.example.food_app.pojo.MealsByCategory
import com.example.food_app.pojo.MealsByCategoryList
import com.example.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CategoryMealsViewModels :ViewModel() {
    private val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                if (response.isSuccessful) {
                   response.body()?.let { mealsList ->
                       mealsLiveData.postValue(mealsList.meals!!)
                    }
                }
            }
            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModels",t.message.toString())
            }
        })
    }

    fun observeMealsLiveData() :LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}

