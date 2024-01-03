package com.example.food_app.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.easyfood.adapters.MostPopularRecyclerAdapter
import com.example.food_app.Adapters.CategoriesAdapter
import com.example.food_app.actiivty.CategoryMealsActivity
import com.example.food_app.actiivty.MealActivity
import com.example.food_app.databinding.FragmentHomeBinding
import com.example.food_app.pojo.Category
import com.example.food_app.pojo.CategoryList
import com.example.food_app.pojo.Meal

import com.example.food_app.viewModel.HomeViewModel


class HomeFragment: Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemAdapter : MostPopularRecyclerAdapter
    private lateinit var categoryAdapter : CategoriesAdapter

    companion object {
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMB = "meal_thumb"
        const val CATEGORY_KEY = "category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        popularItemAdapter = MostPopularRecyclerAdapter()
        categoryAdapter = CategoriesAdapter()
    }
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

        prepareItemRecycleView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()

        homeMvvm.getPopularItem()
        observerPopularItemLiveData()
        onPopularItemClick()

        prepareCategoryRecycleView()
        homeMvvm.getCategories()
        observeCategoryLiveData()
        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_KEY,category)
            startActivity(intent)
        }
    }

    private fun prepareCategoryRecycleView() {
        binding.recyclerViewCategory.adapter = categoryAdapter
    }

    private fun observeCategoryLiveData() {
        homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner,Observer{
                    it?.let {
                        categoryAdapter.setCategoryList(it as List<Category>)
                    }

        })
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { categoryMeal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,categoryMeal.idMeal)
            intent.putExtra(MEAL_NAME,categoryMeal.strMeal)
            intent.putExtra(MEAL_THUMB,categoryMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareItemRecycleView() {
        binding.recViewMealsPopular.adapter = popularItemAdapter
    }

    private fun observerPopularItemLiveData() {
        homeMvvm.observePopularItemLiveData().observe(viewLifecycleOwner, { mealList ->
            popularItemAdapter.setMealList(mealsList = mealList!! as ArrayList)
        })
    }

    private fun onRandomMealClicked() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment).load(value.strMealThumb).into(binding.imgRandomMeal)
                this@HomeFragment.randomMeal = value
                onRandomMealClicked()
            }
        })
    }
}