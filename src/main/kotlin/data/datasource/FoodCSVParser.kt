package org.example.data.datasource

import domain.model.Meal
import domain.model.Nutrition
import java.text.SimpleDateFormat
import java.util.*

class FoodCSVParser {

    private fun splitOnlyLists(foodLine: String): List<String> {
        return foodLine.split("\"[", "]\",")
    }

    private fun splitAll(foodLine: String): List<String> {
        val splittedColumns = mutableListOf<String>()
        val splittedListsOnly = splitOnlyLists(foodLine)
        val listOfNAME_ID_MINUTES_CONTRIBUTOR_ID_SUBMITTED = parseStringList(splittedListsOnly, NAME_ID_MINUTES_CONTRIBUTOR_ID_SUBMITTED)
        splittedColumns.addAll(listOfNAME_ID_MINUTES_CONTRIBUTOR_ID_SUBMITTED.subList(fromIndex = 0, toIndex = listOfNAME_ID_MINUTES_CONTRIBUTOR_ID_SUBMITTED.size-1 ))
        splittedColumns.add(splittedListsOnly[TAGS])
        splittedColumns.addAll(splittedListsOnly.subList(fromIndex = NUTRITION, toIndex = splittedListsOnly.size))
        return splittedColumns
    }

    fun parseFoodLine(foodLine: String): Meal {

        val foodStringList = splitAll(foodLine)
        val nutruition = createNutrition(foodStringList)
        return Meal(
            name = foodStringList[MealIndex.NAME],
            id = foodStringList[MealIndex.ID].toInt(),
            minutes = foodStringList[MealIndex.MINUTES].toInt(),
            contributorId = foodStringList[MealIndex.CONTRIBUTOR_ID].toInt(),
            submitted = parseDate(foodStringList[MealIndex.SUBMITTED]).toString(),
            tags = parseStringList(foodStringList[MealIndex.TAGS]),
            nutrition = nutruition,
            nSteps = foodStringList[MealIndex.N_STEPS].trim(',').toInt(),
            steps = parseStringList(foodStringList[MealIndex.STEPS]),
            description = if(foodStringList.size>MealIndex.DESCRIPTION){ foodStringList[MealIndex.DESCRIPTION]} else null,
            ingredients = if(foodStringList.size>MealIndex.INGREDIENTS){ parseStringList(foodStringList[MealIndex.INGREDIENTS])} else null,
            nIngredients = if(foodStringList.size>MealIndex.N_INGREDIENTS){foodStringList[MealIndex.N_INGREDIENTS].toInt()} else null
        )
    }
    private fun createNutrition(foodStringList: List<String>): Nutrition{
        return Nutrition(
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.CALORIES],
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.TOTAL_FAT],
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.SUGAR],
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.SODIUM],
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.PROTEIN],
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.SATURATED_FAT],
            parseDoubleList(foodStringList[MealIndex.NUTRITION])[NutriutionIndex.CARBOHYDRATES],
        )
    }

    private fun parseDoubleList(listString: String): List<Double> {
        return parseStringList(listString).mapNotNull{it.toDouble()}
    }

    private fun parseDate(dateString: String): Date {
        return DATE_FORMAT.parse(dateString)
    }

    private fun parseStringList(listString: String): List<String> {
        return listString.split(',').mapNotNull{ it.trim() }
    }
    private fun parseStringList(splitOnlyLists: List<String>, index: Int): List<String> {
        return parseStringList(splitOnlyLists[index])
    }

    companion object {
        val NAME_ID_MINUTES_CONTRIBUTOR_ID_SUBMITTED = 0
        val TAGS = 1
        val NOTHING = 2
        val NUTRITION = 3
        const val N_STEPS = 4
        const val STEPS = 5
        const val DESCRIPTION = 6
        const val INGREDIENTS = 7
        const val N_INGREDIENTS = 8
        val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")
    }
}