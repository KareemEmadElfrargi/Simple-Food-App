package com.example.food_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.food_app.Adapters.CategoriesAdapter
import com.example.food_app.actiivty.MainActivity
import com.example.food_app.databinding.FragmentCategoriesBinding
import com.example.food_app.databinding.FragmentFavoritesBinding
import com.example.food_app.databinding.FragmentHomeBinding
import com.example.food_app.viewModel.HomeViewModel

class CategoriesFragment: Fragment() {
    private lateinit var binding : FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycleView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categoriesList ->

            categoriesAdapter.setCategoryList(categoriesList!!)

        })
    }

    private fun prepareRecycleView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recycleViewCategory.adapter =  categoriesAdapter
    }
}