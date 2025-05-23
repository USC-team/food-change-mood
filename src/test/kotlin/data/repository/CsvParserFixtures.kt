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

object CsvParserFixtures {
  fun mealRow() = mutableMapOf<String, String>(
    ID            to "42",
    NAME          to "TestMeal",
    MINUTES       to "15",
    CONTRIBUTOR   to "7",
    SUBMITTED     to "2025-01-01",
    TAGS          to "",
    NUTRITION     to "",
    N_STEPS       to "3",
    STEPS         to "",
    DESCRIPTION   to "desc",
    INGREDIENTS   to "",
    N_INGREDIENTS to "2"
  )
}
