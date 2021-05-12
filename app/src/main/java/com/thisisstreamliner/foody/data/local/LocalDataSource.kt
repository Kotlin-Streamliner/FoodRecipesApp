package com.thisisstreamliner.foody.data.local

import com.thisisstreamliner.foody.data.local.database.RecipesDAO
import com.thisisstreamliner.foody.data.local.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
        private val recipesDao: RecipesDAO
) {

    fun readDatabase(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

    suspend fun insertRecipes(recipesEntity: RecipesEntity) = recipesDao.insertRecipes(recipesEntity)
}