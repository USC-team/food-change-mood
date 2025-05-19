package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.model.Meal
import domain.model.Nutrition
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class GetKetoMealUseCaseTest {

    private lateinit var repository: MealsRepository
    private lateinit var useCase: GetKetoMealUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetKetoMealUseCase(repository)
    }

    @Test
    fun `getKetoMeal returns a random keto meal without duplicate`() {
        // Given
        val keto1 = Meal(
            id = 1,
            name = "Keto Meal 1",
            nutrition = Nutrition(
                carbohydrates = 10.0, protein = 15.0,
                sugar = null,
                sodium = null,
                calories = null,
                totalFat = null,
                saturatedFat = null
            ),
            nSteps = null,
            submitted = null,
            tags = null,
            minutes = null,
            nIngredients = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        val keto2 = Meal(
            id = 2,
            name = "Keto Meal 2",
            nutrition = Nutrition(
                carbohydrates = 8.0, protein = 10.0,
                sugar = null,
                sodium = null,
                calories = null,
                totalFat = null,
                saturatedFat = null
            ),
            nSteps = null,
            submitted = null,
            tags = null,
            minutes = null,
            nIngredients = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        val nonKeto = Meal(
            id = 3,
            name = "Bread",
            nutrition = Nutrition(
                carbohydrates = 20.0, protein = 5.0,
                sugar = null,
                sodium = null,
                calories = null,
                totalFat = null,
                saturatedFat = null
            ),
            nSteps = null,
            submitted = null,
            tags = null,
            minutes = null,
            nIngredients = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        every { repository.getAllMeals() } returns listOf(keto1, keto2, nonKeto)

        // When
        val firstPick = useCase.getKetoMeal()
        val secondPick = useCase.getKetoMeal()

        // Then
        assertThat(secondPick).isIn(listOf(keto1, keto2) - firstPick)
        assertThat(secondPick).isNotEqualTo(firstPick)
    }


    @Test
    fun `getKetoMeal throws exception when no keto meals available`() {
        // Given
        val nonKeto = Meal(
            id = 4,
            name = "Rice",
            nutrition = Nutrition(
                carbohydrates = 40.0, protein = 8.0,
                sugar = null,
                sodium = null,
                calories = null,
                totalFat = null,
                saturatedFat = null
            ),
            nSteps = null,
            submitted = null,
            tags = null,
            minutes = null,
            nIngredients = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        every { repository.getAllMeals() } returns listOf(nonKeto)

        // When && Then
        assertThat(assertThrows<Exception> {
            useCase.getKetoMeal()
        }).hasMessageThat()
            .isEqualTo("No more keto meals available.")
    }

    @Test
    fun `getKetoMeal throws exception when all keto meals are exhausted`() {
        // Given
        val keto1 = Meal(
            id = 5,
            name = "Keto Meal 5",
            nutrition = Nutrition(
                carbohydrates = 5.0, protein = 10.0,
                sugar = null,
                sodium = null,
                calories = null,
                totalFat = null,
                saturatedFat = null
            ),
            nSteps = null,
            submitted = null,
            tags = null,
            minutes = null,
            nIngredients = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        every { repository.getAllMeals() } returns listOf(keto1)

        // When
        val first = useCase.getKetoMeal()

        // Then
        assertThat(first).isEqualTo(keto1)
        assertThat(assertThrows<Exception> {
            useCase.getKetoMeal()
        }).hasMessageThat()
            .isEqualTo("No more keto meals available.")
    }

}