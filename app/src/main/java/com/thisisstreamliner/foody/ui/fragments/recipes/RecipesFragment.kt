package com.thisisstreamliner.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisisstreamliner.foody.viewmodels.MainViewModel
import com.thisisstreamliner.foody.R
import com.thisisstreamliner.foody.adapters.RecipesAdapter
import com.thisisstreamliner.foody.models.NetworkResult
import com.thisisstreamliner.foody.util.Constants
import com.thisisstreamliner.foody.util.observeOnce
import com.thisisstreamliner.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val TAG = "RecipesFragment"

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        Log.e("DEBUG", "mainViewModel = $mainViewModel and recipesViewModel = $recipesViewModel")
        setupRecyclerView()
        readDatabase()
        return mView
    }

    private fun setupRecyclerView() {
        mView.shimmerRecyclerView.adapter = mAdapter
        mView.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, {database ->
                if (!database.isNullOrEmpty()) {
                    Log.d(TAG, "readDatabase")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d(TAG, "requestApiData")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.e("DEBUG", "NetworkResult.Success ${response.data}")
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Log.e("DEBUG", "NetworkResult.Error ${response.message}")
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNullOrEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    private fun showShimmerEffect() {
        mView.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.shimmerRecyclerView.hideShimmer()
    }
}