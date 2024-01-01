package com.example.food_app.actiivty

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.food_app.R
import com.example.food_app.databinding.ActivityMealBinding
import com.example.food_app.fragment.HomeFragment
import com.example.food_app.pojo.Meal
import com.example.food_app.viewModel.HomeViewModel
import com.example.food_app.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealID: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        getInformationFromRandomMealIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetails(mealID)
        observerMealDetailsLiveData()

        onYoutubeImagCLick()
    }

    private fun onYoutubeImagCLick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                responseCase()
                val meal = value
                binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
                binding.tvAreaInfo.text = "Area : ${meal.strArea}"
                binding.tvInstructions.text = meal.strInstructions
                binding.progressBar.visibility = View.GONE

                youtubeLink = meal.strYoutube!!
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.apply {
            title = mealName
            setCollapsedTitleTextColor(resources.getColor(R.color.white))
            setExpandedTitleColor(resources.getColor(R.color.white))
        }

    }

    private fun getInformationFromRandomMealIntent() {
        mealID = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase (){
        binding.apply {
            progressBar.visibility =  View.VISIBLE
            btnSave.visibility =  View.INVISIBLE
            tvInstructions.visibility =  View.INVISIBLE
            tvCategoryInfo.visibility =  View.INVISIBLE
            tvAreaInfo.visibility =  View.INVISIBLE
            imgYoutube.visibility =  View.INVISIBLE
        }

    }
    private fun responseCase(){
        binding.apply {
            progressBar.visibility =  View.INVISIBLE
            btnSave.visibility =  View.VISIBLE
            tvInstructions.visibility =  View.VISIBLE
            tvCategoryInfo.visibility =  View.VISIBLE
            tvAreaInfo.visibility =  View.VISIBLE
            imgYoutube.visibility =  View.VISIBLE
        }
    }
}