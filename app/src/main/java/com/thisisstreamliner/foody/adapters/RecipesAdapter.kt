package com.thisisstreamliner.foody.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thisisstreamliner.foody.databinding.RecipesRowLayoutBinding
import com.thisisstreamliner.foody.models.FoodRecipe
import com.thisisstreamliner.foody.models.Result

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe = emptyList<Result>()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    fun setData(newData: FoodRecipe) {
        recipe = newData.results
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = recipe.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder.from(parent)

    class MyViewHolder(
        private val binding: RecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }
}