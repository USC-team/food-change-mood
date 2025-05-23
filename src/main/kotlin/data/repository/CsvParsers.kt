package data.repository

import data.repository.CsvColumns.CONTRIBUTOR
import data.repository.CsvColumns.DESCRIPTION
import data.repository.CsvColumns.ID
import data.repository.CsvColumns.INGREDIENTS
import data.repository.CsvColumns.MINUTES
import data.repository.CsvColumns.NAME
import data.repository.CsvColumns.NUTRITION
import data.repository.CsvColumns.N_INGREDIENTS
import data.repository.CsvColumns.N_STEPS
import data.repository.CsvColumns.STEPS
import data.repository.CsvColumns.SUBMITTED
import data.repository.CsvColumns.TAGS
import domain.model.Meal
import domain.model.Nutrition

object CsvParsers {

    fun parseToMeal(row: Map<String, String>): Meal {
        val nutritionValues = parseDoubleList(row[NUTRITION])
        return Meal(
            id = row[ID]?.toIntOrNull(),
            name = row[NAME],
            minutes = row[MINUTES]?.toIntOrNull(),
            contributorId = row[CONTRIBUTOR]?.toIntOrNull(),
            submitted = row[SUBMITTED],
            tags = parseStringList(row[TAGS]),
            nutrition = Nutrition(
                calories = nutritionValues?.getOrNull(0),
                totalFat = nutritionValues?.getOrNull(1),
                sugar = nutritionValues?.getOrNull(2),
                sodium = nutritionValues?.getOrNull(3),
                protein = nutritionValues?.getOrNull(4),
                saturatedFat = nutritionValues?.getOrNull(5),
                carbohydrates = nutritionValues?.getOrNull(6),
            ),
            nSteps = row[N_STEPS]?.toIntOrNull(),
            steps = parseStringList(row[STEPS]),
            description = row[DESCRIPTION] ,
            ingredients = parseStringList(row[INGREDIENTS]),
            nIngredients = row[N_INGREDIENTS]?.toIntOrNull()
        )
    }

    private fun parseStringList(raw: String?): List<String>? =
        raw?.trim().let {
            val stripped = it?.removePrefix("[")?.removeSuffix("]")
            // split on commas not inside nested quotes
            stripped?.split(',')
                ?.map { token -> token.trim().trim('\'', '"') }
                ?.filter { it.isNotEmpty() }
        }

    private fun parseDoubleList(raw: String?): List<Double>? =
        raw?.trim()?.removePrefix("[")?.removeSuffix("]")
            ?.split(',')?.mapNotNull { it.trim().toDoubleOrNull() }
}
