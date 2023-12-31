package com.example.food_app.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.food_app.databinding.FragmentHomeBinding
import com.example.food_app.pojo.Meal
import com.example.food_app.pojo.MealList
import com.example.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment() {
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RetrofitInstance.api.getRandomMeal().enqueue(object:Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val randomMeal : Meal = response.body()!!.meals?.get(0) ?: emptyList<Meal>()[0]
                    Log.e("TEST",randomMeal.strImageSource.toString())
                    Glide.with(requireView()).load(randomMeal.strMealThumb).into(binding.imgRandomMeal)
                }else {return}
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TEST",t.message.toString())
            }

        })

    }
}