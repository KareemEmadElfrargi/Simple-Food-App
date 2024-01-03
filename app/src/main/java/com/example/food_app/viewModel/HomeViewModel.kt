package com.example.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_app.pojo.Category
import com.example.food_app.pojo.CategoryList
import com.example.food_app.pojo.MealsByCategoryList
import com.example.food_app.pojo.MealsByCategory
import com.example.food_app.pojo.Meal
import com.example.food_app.pojo.MealList
import com.example.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealsByCategory>?>()
    private var categoriesLiveData = MutableLiveData<List<Category?>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val randomMeal : Meal = response.body()!!.meals?.get(0) ?: emptyList<Meal>()[0]
                    randomMealLiveData.postValue(randomMeal)
                }else {return}
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }

        })
    }

    fun getPopularItem(){
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object:Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body()!=null){
                    popularItemLiveData.value = response.body()!!.meals
                }
            }
            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
               Log.d("Home Fragment",t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData():LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemLiveData():LiveData<List<MealsByCategory>?>{
        return popularItemLiveData

    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                val categoryList = response.body()?.categories
                categoryList?.let {
                    categoriesLiveData.postValue(it)
                }
            }
            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        })
    }

    fun observeCategoryLiveData() :LiveData<List<Category?>> {
        return categoriesLiveData
    }
}