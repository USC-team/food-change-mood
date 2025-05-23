package data.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CsvParsersTest {

    // Given
    private fun baseRow() = mutableMapOf(
        CsvParsers.ID            to "42",
        CsvParsers.NAME          to "TestMeal",
        CsvParsers.MINUTES       to "15",
        CsvParsers.CONTRIBUTOR   to "7",
        CsvParsers.SUBMITTED     to "2025-01-01",
        CsvParsers.TAGS          to "",
        CsvParsers.NUTRITION     to "",
        CsvParsers.N_STEPS       to "3",
        CsvParsers.STEPS         to "",
        CsvParsers.DESCRIPTION   to "desc",
        CsvParsers.INGREDIENTS   to "",
        CsvParsers.N_INGREDIENTS to "2"
    )

    @Test
    fun `parseToMeal with minimal empty lists`() {
        // When
        val meal = CsvParsers.parseToMeal(baseRow())

        // Then
        assertEquals(42,           meal.id)
        assertEquals("TestMeal",   meal.name)
        assertEquals(15,           meal.minutes)
        assertEquals(7,            meal.contributorId)
        assertEquals("2025-01-01", meal.submitted)
        assertEquals(3,            meal.nSteps)
        assertEquals(2,            meal.nIngredients)

        assertEquals(emptyList<String>(), meal.tags)
        assertEquals(emptyList<String>(), meal.steps)
        assertEquals(emptyList<String>(), meal.ingredients)

        with(meal.nutrition) {
            assertNull(this?.calories)
            assertNull(this?.totalFat)
            assertNull(this?.sugar)
            assertNull(this?.sodium)
            assertNull(this?.protein)
            assertNull(this?.saturatedFat)
            assertNull(this?.carbohydrates)
        }
    }

    @Test
    fun `parseToMeal with comma-separated and bracketed tokens`() {
        // Given
        val row = baseRow().apply {
            this[CsvParsers.TAGS]        = "a,b,c"
            this[CsvParsers.STEPS]       = "['s1','s2','s3']"
            this[CsvParsers.INGREDIENTS] = "x,y"
        }
        // When
        val meal = CsvParsers.parseToMeal(row)

        // Then
        assertEquals(listOf("a","b","c"),       meal.tags)
        assertEquals(listOf("s1","s2","s3"),   meal.steps)
        assertEquals(listOf("x","y"),          meal.ingredients)
    }

}