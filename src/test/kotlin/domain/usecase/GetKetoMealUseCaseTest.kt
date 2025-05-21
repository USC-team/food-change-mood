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
    fun `getKetoMeal should throws when nutrition is null`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 10, name = "NullNut", nutrition = null)
        )

        // When && Then
        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal should throws when protein is null`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 11,
                name = "NoProtein",
                nutrition = createNutrition(carbohydrates = 5.0, protein = null)
            )
        )

        // When && Then
        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal should includes exactly when at keto thresholds`() {
        // Given
        val ketoMeal = createMeal(
            id = 12,
            name = "BoundaryKeto",
            nutrition = createNutrition(carbohydrates = 15.0, protein = 10.0)
        )
        every { repository.getAllMeals() } returns listOf(ketoMeal)
        // When
        val result = useCase.getKetoMeal()

        // Then
        assertThat(result).isEqualTo(ketoMeal)
    }

    @Test
    fun `getKetoMeal should returns two distinct then exhausts`() {
        // Given
        val keto1 = createMeal(20, "K1", createNutrition(5.0, 10.0))
        val keto2 = createMeal(21, "K2", createNutrition(10.0, 12.0))
        every { repository.getAllMeals() } returns listOf(keto1, keto2)

        // When
        val seen = setOf(useCase.getKetoMeal(), useCase.getKetoMeal())

        // Then
        assertThat(seen).containsExactly(keto1, keto2)
        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal should excludes when carbs above threshold`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 30,
                name = "TooCarby",
                nutrition = createNutrition(carbohydrates = 16.0, protein = 20.0)
            )
        )

        // When && Then
        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal excludes when protein below threshold`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 31,
                name = "TooLowProtein",
                nutrition = createNutrition(carbohydrates = 5.0, protein = 9.0)
            )
        )

        // When && Then
        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
    }

    @Test
    fun `getKetoMeal excludes when carbohydrates is null`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 50,
                name = "NullCarb",
                nutrition = createNutrition(carbohydrates = null, protein = 12.0)
            )
        )

        // When & Then
        assertThrows<MealNotFoundExceptions> {
            useCase.getKetoMeal()
        }
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
        assertThat(result).isAnyOf(keto1,keto2)
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