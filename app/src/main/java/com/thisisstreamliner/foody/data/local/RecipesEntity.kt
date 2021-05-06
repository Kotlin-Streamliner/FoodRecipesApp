package com.thisisstreamliner.foody.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thisisstreamliner.foody.models.FoodRecipe
import com.thisisstreamliner.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity (
        var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}