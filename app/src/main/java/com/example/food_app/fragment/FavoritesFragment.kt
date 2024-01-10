package com.example.food_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.Adapters.MealAdapter
import com.example.food_app.actiivty.MainActivity
import com.example.food_app.databinding.FragmentFavoritesBinding
import com.example.food_app.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment: Fragment() {
    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouriteAdapter: MealAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycleView()
        observeFavourite()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition


                    val currentMeal = favouriteAdapter.differ.currentList[position]
                    viewModel.deleteMeal(currentMeal)
                    binding.numberOfList.text = (favouriteAdapter.differ.currentList.size -1).toString()

                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo",
                        View.OnClickListener {
                            // Check if the position is still valid before undoing
                            if (position < favouriteAdapter.differ.currentList.size) {
                                viewModel.insertMeal(currentMeal)
                                favouriteAdapter.notifyItemInserted(position)
                            } else {
                                // Handle the case where the position is no longer valid (e.g., the list has changed)
                                // You may choose to display a message or take appropriate action.
                            }
                        }
                    ).show()

            }


        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun prepareRecycleView() {
        favouriteAdapter = MealAdapter()
        binding.rvFavourites.adapter = favouriteAdapter
    }

    private fun observeFavourite() {
        viewModel.observeFavoritesMealLiveData().observe(requireActivity(), Observer { mealList ->
            favouriteAdapter.differ.submitList(mealList)
            binding.numberOfList.text = favouriteAdapter.differ.currentList.size.toString()
        })
    }
}