package data.repository

import domain.model.Meal
import domain.model.Nutrition

object CsvParsers {

    fun parseToMeal(row: Map<String, String>): Meal {
        val nutritionValues = parseDoubleList(row[NUTRITION] ?: "")
        return Meal(
            id = row[ID]?.toInt() ?: 0,
            name = row[NAME] ?: "",
            minutes = row[MINUTES]?.toInt(),
            contributorId = row[CONTRIBUTOR]?.toInt(),
            submitted = row[SUBMITTED] ?: "",
            tags = parseStringList(row[TAGS] ?: ""),
            nutrition = Nutrition(
                calories = nutritionValues.getOrNull(0),
                totalFat = nutritionValues.getOrNull(1),
                sugar = nutritionValues.getOrNull(2),
                sodium = nutritionValues.getOrNull(3),
                protein = nutritionValues.getOrNull(4),
                saturatedFat = nutritionValues.getOrNull(5),
                carbohydrates = nutritionValues.getOrNull(6),
            ),
            nSteps = row[N_STEPS]?.toInt(),
            steps = parseStringList(row[STEPS] ?: ""),
            description = row[DESCRIPTION] ?: "",
            ingredients = parseStringList(row[INGREDIENTS] ?: ""),
            nIngredients = row[N_INGREDIENTS]?.toInt()
        )
    }

    private fun parseStringList(raw: String): List<String> =
        raw.trim().let {
            val stripped = it.removePrefix("[").removeSuffix("]")
            // split on commas not inside nested quotes
            stripped
                .split(',')
                .map { token -> token.trim().trim('\'', '"') }
                .filter { it.isNotEmpty() }
        }

    private fun parseDoubleList(raw: String): List<Double> =
        raw.trim().removePrefix("[").removeSuffix("]")
            .split(',')
            .mapNotNull { it.trim().toDoubleOrNull() }

    const val ID = "id"
    const val NAME = "name"
    const val MINUTES = "minutes"
    const val CONTRIBUTOR = "contributor_id"
    const val SUBMITTED = "submitted"
    const val TAGS = "tags"
    const val NUTRITION = "nutrition"
    const val N_STEPS = "n_steps"
    const val STEPS = "steps"
    const val DESCRIPTION = "description"
    const val INGREDIENTS = "ingredients"
    const val N_INGREDIENTS = "n_ingredients"
}
