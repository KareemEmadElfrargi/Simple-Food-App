package com.example.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_app.pojo.Meal
import com.example.food_app.pojo.MealList
import com.example.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel:ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object:Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    mealDetailsLiveData.postValue(response.body()!!.meals?.get(0))
                }else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Meal Activity",t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

}