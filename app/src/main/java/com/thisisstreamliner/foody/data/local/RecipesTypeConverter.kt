package com.thisisstreamliner.foody.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thisisstreamliner.foody.models.FoodRecipe

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String = gson.toJson(foodRecipe)

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe = gson.fromJson(data, object: TypeToken<FoodRecipe>(){}.type)
}