package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.usecase.exceptions.MealNotFoundExceptions
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class GetKetoMealUseCaseTest {

    private var repository: MealsRepository = mockk(relaxed = true)
    private lateinit var useCase: GetKetoMealUseCase

    @BeforeEach
    fun setup() {
        useCase = GetKetoMealUseCase(repository)
    }

    @Test
    fun `getKetoMeal should throws exception when no keto meals available`() {
        // Given
        every { repository.getAllMeals() } returns emptyList()

        // When && Then
        assertThrows<MealNotFoundExceptions> { useCase.getKetoMeal() }
    }

    @Test
    fun `getKetoMeal should throws exception when not meets keto meal criteria`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 4,
                name = "Rice",
                nutrition = createNutrition(carbohydrates = 40.0, protein = 8.0),
            )
        )

        // When && Then
        assertThrows<MealNotFoundExceptions> { useCase.getKetoMeal() }
    }

    @Test
    fun `getKetoMeal should returns a random keto meal when meets keto meal criteria`() {
        // Given
        val keto1 = createMeal(
            id = 1,
            name = "Keto Meal 1",
            nutrition = createNutrition(carbohydrates = 10.0, protein = 15.0),
        )
        val keto2 = createMeal(
            id = 2,
            name = "Keto Meal 2",
            nutrition = createNutrition(carbohydrates = 8.0, protein = 10.0),
        )
        val nonKeto = createMeal(
            id = 3,
            name = "Bread",
            nutrition = createNutrition(carbohydrates = 20.0, protein = 5.0),
        )
        every { repository.getAllMeals() } returns listOf(keto1, keto2, nonKeto)

        // When
        val result = useCase.getKetoMeal()

        // Then
        assertThat(result).isEqualTo(keto1)
    }

    @Test
    fun `getKetoMeal should throws exception when all keto meals are exhausted`() {
        // Given
        val keto1 = createMeal(
            id = 5,
            name = "Keto Meal 5",
            nutrition = createNutrition(carbohydrates = 5.0, protein = 10.0)
        )
        every { repository.getAllMeals() } returns listOf(keto1)

        // When
        val first = useCase.getKetoMeal()

        // Then
        assertThat(first).isEqualTo(keto1)

        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
    }
}