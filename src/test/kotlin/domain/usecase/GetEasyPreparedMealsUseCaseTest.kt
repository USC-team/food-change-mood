package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetEasyPreparedMealsUseCaseTest {

    private var repository: MealsRepository = mockk(relaxed = true)
    private lateinit var useCase: GetEasyPreparedMealsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetEasyPreparedMealsUseCase(repository)
    }

    @Test
    fun `getEasyPrepared should returns empty list when repository has no meals`() {
        // Given
        every { repository.getAllMeals() } returns emptyList()

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getEasyPrepared should returns all easy prepared meals when meets easy criteria`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Smoothie", minutes = 5, nIngredients = 4, nSteps = 1),
            createMeal(id = 3, name = "Smoothie", minutes = 45, nIngredients = 4, nSteps = 1),
            createMeal(id = 4, name = "Smoothie", minutes = 5, nIngredients = 10, nSteps = 1),
            createMeal(id = 5, name = "Smoothie", minutes = 5, nIngredients = 4, nSteps = 20)
        )

        // When
        val result = useCase.getEasyPreparedMeals()

        //Then
        assertThat(result.map { it.id }).containsExactlyElementsIn(listOf(1, 2))
    }

    @Test
    fun `getEasyPrepared should returns only items that meets easyMeal criteria`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Easy", minutes = 15, nIngredients = 2, nSteps = 3),
            createMeal(id = 2, name = "TooLong", minutes = 45, nIngredients = 2, nSteps = 3),
            createMeal(id = 3, name = "TooManyIngredients", minutes = 10, nIngredients = 8, nSteps = 3),
            createMeal(id = 4, name = "TooManySteps", minutes = 10, nIngredients = 2, nSteps = 10)
        )

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result.map { it.id }).containsExactly(1)
    }


    @Test
    fun `getEasyPrepared should returns empty list when no meals meet easy criteria`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Complex Dish", minutes = 60, nIngredients = 10, nSteps = 12)
        )

        //When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `returns exactly ten distinct easy meals when more than ten available`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Easy1", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 2, name = "Easy2", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 3, name = "Easy3", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 4, name = "Easy4", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 5, name = "Easy5", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 6, name = "Easy6", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 7, name = "Easy7", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 8, name = "Easy8", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 9, name = "Easy9", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 10, name = "Easy10", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 11, name = "Easy11", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 12, name = "Easy12", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 13, name = "Easy13", minutes = 20, nIngredients = 5, nSteps = 6),
            createMeal(id = 14, name = "Easy14", minutes = 20, nIngredients = 5, nSteps = 6),
        )

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result.toSet()).hasSize(10)
    }


}