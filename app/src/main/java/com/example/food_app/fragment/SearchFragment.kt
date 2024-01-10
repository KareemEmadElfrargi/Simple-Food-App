package com.example.food_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.food_app.Adapters.MealAdapter
import com.example.food_app.R
import com.example.food_app.actiivty.MainActivity
import com.example.food_app.databinding.FragmentSearchBinding
import com.example.food_app.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: MealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycleView()

        binding.imSearchIcon.setOnClickListener {
            searchMeals()
        }
        observeSearchMealsLiveData()


        var searchJob : Job?=null
        binding.edSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun observeSearchMealsLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            searchAdapter.differ.submitList(mealList)

        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString().trim()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecycleView() {
        searchAdapter = MealAdapter()
        binding.rvSearchMeal.adapter = searchAdapter
    }

}