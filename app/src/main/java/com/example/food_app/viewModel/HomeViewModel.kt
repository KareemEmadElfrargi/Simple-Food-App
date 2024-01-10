package com.example.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.db.MealDatabase
import com.example.food_app.pojo.Category
import com.example.food_app.pojo.CategoryList
import com.example.food_app.pojo.MealsByCategoryList
import com.example.food_app.pojo.MealsByCategory
import com.example.food_app.pojo.Meal
import com.example.food_app.pojo.MealList
import com.example.food_app.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(
    private val mealDatabase: MealDatabase
):ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealsByCategory>?>()
    private var categoriesLiveData = MutableLiveData<List<Category?>>()
    private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMutableLiveData = MutableLiveData<Meal>()
    private var searchMealLiveData = MutableLiveData<List<Meal>>()


    init {
        getRandomMeal()
    }

    var saveRandomMealLiveData:Meal?=null
    fun getRandomMeal(){
        saveRandomMealLiveData?.let { meal ->
            randomMealLiveData.postValue(meal)
            return
        }
        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val randomMeal : Meal = response.body()!!.meals?.get(0) ?: emptyList<Meal>()[0]
                    randomMealLiveData.postValue(randomMeal)
                    saveRandomMealLiveData = randomMeal
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

    fun observeFavoritesMealLiveData():LiveData<List<Meal>> {
        return favoritesMealLiveData
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }
    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object  : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let{ meal ->
                    bottomSheetMutableLiveData.postValue(meal)
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.i("HomeViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observeBottomSheetLiveData():LiveData<Meal>{
        return bottomSheetMutableLiveData
    }

    fun searchMeal(searchQuery:String){
        RetrofitInstance.api.searchMeal(searchQuery).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                //
            }

        })
    }

    fun observeSearchMealLiveData():LiveData<List<Meal>>{
        return searchMealLiveData
    }
}