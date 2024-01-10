package com.example.food_app.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.food_app.actiivty.MainActivity
import com.example.food_app.actiivty.MealActivity
import com.example.food_app.databinding.FragmentMealBottomSheetBinding
import com.example.food_app.fragment.HomeFragment
import com.example.food_app.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "mealId"
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    lateinit var binding : FragmentMealBottomSheetBinding
    private lateinit var viewModel : HomeViewModel

    private var mealName :String?=null
    private var mealThumb:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMealById(mealId!!)

        observeBottomSheetMeal()
        onBottomSheetClick()

    }
    private fun onBottomSheetClick() {
        binding.readMore.setOnClickListener {
            if (mealName !=null && mealThumb!=null){
                val intent = Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)

                }
                startActivity(intent)
            }
        }

    }

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner, Observer { meal ->
            meal.let {
                Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
                binding.tvBottomSheetArea.text = meal.strArea
                binding.tvBottomSheetCategory.text = meal.strCategory
                binding.nameOfMeal.text = meal.strMeal

                mealName = meal.strMeal
                mealThumb = meal.strMealThumb
            }
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, id)
                }
            }
    }
}