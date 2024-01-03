package com.example.food_app.actiivty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.food_app.Adapters.CategoryMealsAdapter
import com.example.food_app.databinding.ActivityCategoryMealsBinding
import com.example.food_app.fragment.HomeFragment.Companion.CATEGORY_KEY
import com.example.food_app.pojo.Category
import com.example.food_app.viewModel.CategoryMealsViewModels

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModels
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecycleView()
        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModels::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getParcelableExtra<Category>(CATEGORY_KEY)?.strCategory!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealList(mealsList)
        })
    }

    private fun prepareRecycleView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.adapter = categoryMealsAdapter
    }
}