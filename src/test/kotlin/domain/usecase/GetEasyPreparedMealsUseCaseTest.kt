package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.model.Meal
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetEasyPreparedMealsUseCaseTest {

    private lateinit var repository: MealsRepository
    private lateinit var useCase: GetEasyPreparedMealsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
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
        val meal1 = Meal(
            id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2,
            nutrition = null,
            submitted = null,
            tags = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        val meal2 = Meal(
            id = 2, name = "Smoothie", minutes = 5, nIngredients = 4, nSteps = 1,
            nutrition = null,
            submitted = null,
            tags = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        every { repository.getAllMeals() } returns listOf(meal1, meal2)

        // When
        val result = useCase.getEasyPreparedMeals()

        //Then
        assertThat(result).hasSize(2)
    }

    @Test
    fun `getEasyPrepared should returns 1 item from list when meet easy criteria`() {
        // Given
        val meals = listOf(
            Meal(
                id = 1, name = "Easy", minutes = 15, nIngredients = 2, nSteps = 3,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 2, name = "TooLong", minutes = 45, nIngredients = 2, nSteps = 3,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 3, name = "TooManyIngredients", minutes = 10, nIngredients = 8, nSteps = 3,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 4, name = "TooManySteps", minutes = 10, nIngredients = 2, nSteps = 10,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            )
        )
        every { repository.getAllMeals() } returns meals

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result).hasSize(1)
    }


    @Test
    fun `getEasyPrepared should returns empty list when no meals meet easy criteria`() {
        // Given
        val meal = Meal(
            id = 1,
            name = "Complex Dish",
            minutes = 60,
            nIngredients = 10,
            nSteps = 12,
            nutrition = null,
            submitted = null,
            tags = null,
            steps = null,
            description = null,
            ingredients = null,
            contributorId = null
        )
        every { repository.getAllMeals() } returns listOf(meal)

        //When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `returns exactly ten distinct easy meals when more than ten available`() {
        // Given
        val allEasy = listOf(
            Meal(
                id = 1, name = "Easy1", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 2, name = "Easy2", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 3, name = "Easy3", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 4, name = "Easy4", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 5, name = "Easy5", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 6, name = "Easy6", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 7, name = "Easy7", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 8, name = "Easy8", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 9, name = "Easy9", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 10, name = "Easy10", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 11, name = "Easy11", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 12, name = "Easy12", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 13, name = "Easy13", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
            Meal(
                id = 14, name = "Easy14", minutes = 20, nIngredients = 5, nSteps = 6,
                nutrition = null,
                submitted = null,
                tags = null,
                steps = null,
                description = null,
                ingredients = null,
                contributorId = null
            ),
        )
        every { repository.getAllMeals() } returns allEasy

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result).hasSize(10)
    }


}