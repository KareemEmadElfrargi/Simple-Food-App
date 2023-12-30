package com.example.food_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.food_app.databinding.FragmentCategoriesBinding
import com.example.food_app.databinding.FragmentFavoritesBinding
import com.example.food_app.databinding.FragmentHomeBinding

class CategoriesFragment: Fragment() {
    private lateinit var binding : FragmentCategoriesBinding


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
    }
}