package com.example.food_app.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.databinding.MealCardBinding
import com.example.food_app.pojo.MealsByCategory

class CategoryMealsAdapter :RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealViewHolder>() {
    private var mealList =  ArrayList<MealsByCategory>()
    fun setMealList(mealList: List<MealsByCategory>) {
        this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealViewHolder(val binding: MealCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(MealCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(mealList[position].strMealThumb)
                .into(imgMeal)
            tvMealName.text = mealList[position].strMeal
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}