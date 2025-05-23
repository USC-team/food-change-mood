package data.repository

import data.repository.CsvColumns.CONTRIBUTOR
import data.repository.CsvColumns.INGREDIENTS
import data.repository.CsvColumns.N_INGREDIENTS
import data.repository.CsvColumns.N_STEPS
import data.repository.CsvColumns.STEPS
import data.repository.CsvColumns.TAGS
import data.repository.CsvParserFixtures.mealRow
import domain.model.Meal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CsvParsersTest {

    private lateinit var base: MutableMap<String, String>
    private lateinit var meal: Meal

    @BeforeEach
    fun setup() {
        // Given
        base = mealRow()
        meal = CsvParsers.parseToMeal(base)
    }

    @Test
    fun `should return meal with empty lists when all list-fields are blank`() {
        // Then
        assertEquals(42, meal.id)

    }

    @Test
    fun `should return empty ingredients when none provided`() {
        assertEquals(emptyList<String>(), meal.ingredients)
    }

    @Test
    fun `should return correct submitted date`() {
        assertEquals("2025-01-01", meal.submitted)
    }

    @Test
    fun `should have null nutrition fields when nutrition blank`() {
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
    fun `should return null contributorId when contributor missing or blank`() {
        // Given
        base[CONTRIBUTOR] = ""
        // When
        val meal = CsvParsers.parseToMeal(base)
        // Then
        assertNull(meal.contributorId)
    }

    @Test
    fun `should return null nSteps when steps count missing or blank`() {
        // Given
        base[N_STEPS] = ""
        // When
        val meal = CsvParsers.parseToMeal(base)
        // Then
        assertNull(meal.nSteps)
    }

    @Test
    fun `should return null nIngredients when ingredients count missing or blank`() {
        // Given
        base[N_INGREDIENTS] = ""
        // When
        val meal = CsvParsers.parseToMeal(base)
        // Then
        assertNull(meal.nIngredients)
    }

    @Test
    fun `should parse tags correctly when provided`() {
        // Given
        val row = mealRow().apply { this[TAGS] = "a,b,c" }
        // When
        val updatedMeal = CsvParsers.parseToMeal(row)
        // Then
        assertEquals(listOf("a", "b", "c"), updatedMeal.tags)
    }

    @Test
    fun `should parse steps correctly when provided`() {
        // Given
        val row = mealRow().apply { this[STEPS] = "['s1','s2']" }
        // When
        val updatedMeal = CsvParsers.parseToMeal(row)
        // Then
        assertEquals(listOf("s1", "s2"), updatedMeal.steps)
    }

    @Test
    fun `should parse ingredients correctly when provided`() {
        // Given
        val row = mealRow().apply { this[INGREDIENTS] = "x,y" }
        // When
        val updatedMeal = CsvParsers.parseToMeal(row)
        // Then
        assertEquals(listOf("x", "y"), updatedMeal.ingredients)
    }

}