package data.repository

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class MealsRepositoryImplementationTest {
    private val mockCsvReader = mockk<CsvReader>()
    private val dummyFile = mockk<File>()

    @Test
    fun `getAllMeals should parse valid CSV rows into Meal objects`() {
        // Given
        val fakeCsvData = listOf(
            mapOf("id" to "1", "name" to "Salad", "minutes" to "10", "nIngredients" to "3", "nSteps" to "2"),
            mapOf("id" to "2", "name" to "Pasta", "minutes" to "20", "nIngredients" to "5", "nSteps" to "4")
        )

        every { mockCsvReader.readAllWithHeader(dummyFile) } returns fakeCsvData

        val repo = MealsRepositoryImplementation(mockCsvReader, dummyFile)

        // When
        val result = repo.getAllMeals()

        // Then
        assertEquals(2, result.size)
        assertEquals("Salad", result[0].name)
        assertEquals("Pasta", result[1].name)
    }
}