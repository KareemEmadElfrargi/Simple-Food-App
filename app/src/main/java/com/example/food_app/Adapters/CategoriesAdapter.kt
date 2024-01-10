package com.example.food_app.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.databinding.CategoryCardBinding
import com.example.food_app.pojo.Category
import com.example.food_app.pojo.CategoryList

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoryList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    fun setCategoryList(categoryList: List<Category?>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    inner class CategoryViewHolder(val binding:CategoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryCardBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }
    override fun getItemCount(): Int  = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(categoryList[position])
        }
    }

}