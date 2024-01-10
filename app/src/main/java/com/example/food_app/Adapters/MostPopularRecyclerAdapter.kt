package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.databinding.MostPopularCardBinding
import com.example.food_app.pojo.MealsByCategory

class MostPopularRecyclerAdapter : RecyclerView.Adapter<MostPopularRecyclerAdapter.MostPopularMealViewHolder>(){
    private var mealsList: List<MealsByCategory> = ArrayList()
    lateinit var onItemClick: ((MealsByCategory)->Unit)
    var onLongItemClick:((MealsByCategory)->Unit)?=null

    fun setMealList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }


    class MostPopularMealViewHolder(val binding: MostPopularCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularMealViewHolder {
        return MostPopularMealViewHolder(MostPopularCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MostPopularMealViewHolder, position: Int) {
        val i = position
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(mealsList[position].strMealThumb)
                .into(imgPopularMeal)

        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                onLongItemClick?.invoke(mealsList[position])
                return true
            }

        })
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}

//interface OnItemClick{
//    fun onItemClick(meal:CategoryMeals)
//}

interface OnLongItemClick{
    fun onItemLongClick(meal:MealsByCategory)
}