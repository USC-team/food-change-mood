package org.example.data.repository

import domain.model.Meal
import domain.repository.FoodRepository
import org.example.data.datasource.*


class FoodCSVRepository(val CSVFileName: String) : FoodRepository {
    override fun getAllMeals(): List<Meal> {
        val mealList = mutableListOf<Meal>()
        val foodReader :List<String> = FoodCSVReader(CSVFileName).readFile()
        foodReader.forEach {
            val foodParser= FoodCSVParser().parseFoodLine(it)
            mealList.add(foodParser)
        }
        return mealList
    }
}