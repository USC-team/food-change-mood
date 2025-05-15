package data.repository

import domain.model.Meal
import domain.model.Nutrition

object CsvParsers {

  fun parseToMeal(row: Map<String, String>): Meal {
    val nutritionValues = parseDoubleList(row["nutrition"] ?: "")
    return Meal(
      name = row["name"] ?: "",
      id = row["id"]?.toInt() ?: 0,
      minutes = row["minutes"]?.toInt(),
      contributorId = row["contributor_id"]?.toInt(),
      submitted = row["submitted"] ?: "",
      tags = parseStringList(row["tags"] ?: ""),
      nutrition = Nutrition(
        calories = nutritionValues.getOrNull(0),
        totalFat =nutritionValues.getOrNull(1),
        sugar = nutritionValues.getOrNull(2),
        sodium = nutritionValues.getOrNull(3),
        protein = nutritionValues.getOrNull(4),
        saturatedFat = nutritionValues.getOrNull(5),
        carbohydrates = nutritionValues.getOrNull(6),
      ),
      nSteps = row["n_steps"]?.toInt(),
      steps = parseStringList(row["steps"] ?: ""),
      description = row["description"] ?: "",
      ingredients = parseStringList(row["ingredients"] ?: ""),
      nIngredients = row["n_ingredients"]?.toInt()
    )
  }

  /**
   * Turns "['a','b','c']" into List("a","b","c"),
   * or `"a,b,c"` (if un-bracketed) into List("a","b","c")
   */
  private fun parseStringList(raw: String): List<String> =
    raw.trim().let {
      val stripped = it.removePrefix("[").removeSuffix("]")
      // split on commas not inside nested quotes
      stripped
        .split(',')
        .map { token -> token.trim().trim('\'','"') }
        .filter { it.isNotEmpty() }
    }

  /**
   * Turns "[1.0,2.5,3]" into List(1.0,2.5,3.0)
   */
  private fun parseDoubleList(raw: String): List<Double> =
    raw.trim().removePrefix("[").removeSuffix("]")
      .split(',')
      .mapNotNull { it.trim().toDoubleOrNull() }
}
