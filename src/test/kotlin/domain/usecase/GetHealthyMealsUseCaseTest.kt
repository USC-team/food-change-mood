package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetHealthyMealsUseCase
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetHealthyMealsUseCaseTest {
    private val repository: MealsRepository = mockk(relaxed = true)
    private lateinit var healthyMealsUseCase: GetHealthyMealsUseCase

    @BeforeEach
    fun setup() {
        healthyMealsUseCase = GetHealthyMealsUseCase(repository)
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals empty`() {
        //Given
        every { repository.getAllMeals() } returns emptyList()
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return only meals with less than 15 min list when get list of meals`() {
        //Given
        val nutritions = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, minutes = 1, nutrition = nutritions),
            createMeal(id = 2, minutes = 10, nutrition = nutritions),
            createMeal(id = 3, minutes = 15, nutrition = nutritions),
            createMeal(id = 4, minutes = 17, nutrition = nutritions),
            createMeal(
                id = 5, minutes = 20,
                nutrition = createNutrition(totalFat = 80.0, saturatedFat = 80.0, carbohydrates = 80.0)
            ),
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        val resultIds = result.map { it.id }
        //Then
        assertThat(resultIds).isEqualTo(listOf(1, 2, 3))
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take less than zero min`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1, minutes = 0,
                nutrition = createNutrition(totalFat = 80.0, saturatedFat = 80.0, carbohydrates = 80.0)
            ),
            createMeal(
                id = 2, minutes = 0,
                nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 2, minutes = -1,
                nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 2, minutes = -20,
                nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take null minutes`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1, minutes = null,
                nutrition = createNutrition(totalFat = 80.0, saturatedFat = 80.0, carbohydrates = 80.0)
            ),
            createMeal(
                id = 2, minutes = null,
                nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }


    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take null nutrition`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1, minutes = 15,
                nutrition = null
            ),
            createMeal(
                id = 2, minutes = 1,
                nutrition = null
            ),
            createMeal(
                id = 3, minutes = 14,
                nutrition = createNutrition(totalFat = 80.0, saturatedFat = 80.0, carbohydrates = 80.0)
            ),
            createMeal(
                id = 4, minutes = 5,
                nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        val resultIds = result.map { it.id }
        //Then
        assertThat(resultIds).isEqualTo(listOf(4))
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return meals with less than average total fat when get meals with multiple nutritions`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1, nutrition = createNutrition(totalFat = 100.0, saturatedFat = 100.0, carbohydrates = 100.0)
            ),
            createMeal(
                id = 2, nutrition = createNutrition(totalFat = 100.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 3, nutrition = createNutrition(totalFat = 120.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 4, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 5, nutrition = createNutrition(totalFat = 10.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 6, nutrition = createNutrition(totalFat = null, saturatedFat = 20.0, carbohydrates = 20.0)
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        val resultIds = result.map { it.id }
        //Then
        assertThat(resultIds).isEqualTo(listOf(4, 5))
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return meals with less than average saturated fat when get meals with multiple nutritions`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1, nutrition = createNutrition(totalFat = 100.0, saturatedFat = 100.0, carbohydrates = 100.0)
            ),
            createMeal(
                id = 2, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 100.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 3, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 120.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 4, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 5, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 10.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 6, nutrition = createNutrition(totalFat = 20.0, saturatedFat = null, carbohydrates = 20.0)
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        val resultIds = result.map { it.id }
        //Then
        assertThat(resultIds).isEqualTo(listOf(4, 5))
    }


    @Test
    fun `getHealthyQuickMealsBelowAverage should return meals with less than average carbohydrates when get meals with multiple nutritions`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1, nutrition = createNutrition(totalFat = 100.0, saturatedFat = 100.0, carbohydrates = 100.0)
            ),
            createMeal(
                id = 2, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 100.0)
            ),
            createMeal(
                id = 3, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 120.0)
            ),
            createMeal(
                id = 4, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 20.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 5, nutrition = createNutrition(totalFat = 20.0, saturatedFat = 10.0, carbohydrates = 20.0)
            ),
            createMeal(
                id = 6, nutrition = createNutrition(totalFat = 20.0, saturatedFat = null, carbohydrates = 20.0)
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        val resultIds = result.map { it.id }
        //Then
        assertThat(resultIds).isEqualTo(listOf(4, 5))
    }
}