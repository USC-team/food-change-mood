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

    // these literals mirror your companion‐object constants:
    private val MAX_MINUTES = 30
    private val MAX_INGREDIENTS = 5
    private val MAX_STEPS = 6
    private val TEN = 10

    @BeforeEach
    fun setup() {
        useCase = GetEasyPreparedMealsUseCase(repository)
    }

    @Test
    fun `getEasyPrepared should exclude when minutes bigger than threshold`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(1, "namee", minutes = MAX_MINUTES + 1, nIngredients = 1, nSteps = 1)
        )
        // When && Then
        assertThat(useCase.getEasyPreparedMeals()).isEmpty()
    }

    @Test
    fun `getEasyPrepared should exclude when ingredients bigger than threshold`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(2, "nameee", minutes = 1, nIngredients = MAX_INGREDIENTS + 1, nSteps = 1),
        )
        // When && Then
        assertThat(useCase.getEasyPreparedMeals()).isEmpty()
    }

    @Test
    fun `getEasyPrepared should exclude when steps bigger than threshold`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(3, "thstt", minutes = 1, nIngredients = 1, nSteps = MAX_STEPS + 1)
        )
        // When && Then
        assertThat(useCase.getEasyPreparedMeals()).isEmpty()
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
    fun `getEasyPrepared should returns empty list when meals has low quality data`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Complex Dish", minutes = null, nIngredients = null, nSteps = null)
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

    @Test
    fun `getEasyPrepared should returns exactly the easy prepared meal when meets easy criteria`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 42,
                name = "JustOnTime",
                minutes = 30,
                nIngredients = 5,
                nSteps = 6
            )
        )

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result.map { it.id }).containsExactly(42)
    }

    @Test
    fun `getEasyPrepared should excludes meals with negative or nonsensical data below zero`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 7, "testmeal", minutes = 0, nIngredients = 0, nSteps = 0),
            createMeal(id = 8, "testmeal2", minutes = -5, nIngredients = -1, nSteps = -99)
        )

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result.map { it.id }).containsExactly(7)
    }

    @Test
    fun `getEasyPrepared should excludes meal when any one field is null`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 101, "testmeal1", minutes = null, nIngredients = 3, nSteps = 2),
            createMeal(id = 102, "testmeal2", minutes = 10, nIngredients = null, nSteps = 2),
            createMeal(id = 103, "testmeal3", minutes = 10, nIngredients = 3, nSteps = null),
            createMeal(id = 104, "testmeal4", minutes = 10, nIngredients = 3, nSteps = 2)
        )

        // When
        val result = useCase.getEasyPreparedMeals()

        // Then
        assertThat(result.map { it.id }).containsExactly(104)
    }
}