package domain.usecase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import org.example.domain.usecase.model.MealNotFoundExceptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class GetSweetsWithNoEggsUseCaseTest {
    private var repository: MealsRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSweetsWithNoEggsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetSweetsWithNoEggsUseCase(repository)
    }
    @Test
    fun `should throw MealNotFoundExceptions exception when meals list is empty`() {
        // Given
        every { repository.getAllMeals() } returns emptyList()

        // When &Then
        assertThrows<MealNotFoundExceptions>{ useCase.getMealHasNoEggs()}
    }
    @Test
    fun `should throw MealNotFoundExceptions exception when meals in the list has an empty list of ingredients`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Eggs", ingredients = emptyList())
        )
        // When &Then
        assertThrows<MealNotFoundExceptions>{ useCase.getMealHasNoEggs()}
    }
    @Test
    fun `should throw MealNotFoundExceptions exception when meals in the list has no ingredients`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Eggs")
        )
        // When &Then
        assertThrows<MealNotFoundExceptions>{ useCase.getMealHasNoEggs()}
    }
    @ParameterizedTest
    @ValueSource(strings = ["Egg", "egg", "eggs","Eggs", "EgG"])
    fun `should throw MealNotFoundExceptions exception when meals in the list all have eggs in ingredients`(egg:String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Eggs", ingredients = listOf(egg))
        )
        // When &Then
        assertThrows<MealNotFoundExceptions>{ useCase.getMealHasNoEggs()}
    }
    @ParameterizedTest
    @ValueSource(strings = ["Egg", "egg", "eggs","Eggs", "EgG","EGG","eGG", "eGg"])
    fun `should throw MealNotFoundExceptions exception when meals in the list all have eggs in ingredients with other ingredients`(egg:String) {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Eggs", ingredients = listOf(egg,"Oil"))
        )
        // When &Then
        assertThrows<MealNotFoundExceptions>{ useCase.getMealHasNoEggs()}
    }
    @Test
    fun `should return no egg meal when meals with no eggs in ingredients found`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "no Eggs", ingredients = listOf("Tomato", "oil"))
        )
        // When
        val meal= useCase.getMealHasNoEggs()
        //Then
        assertThat(meal).isNotNull()
    }
    @Test
    fun `should return no egg meal when meals with eggs and no eggs in ingredients found`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "no Eggs", ingredients = listOf("Tomato", "oil")),
            createMeal(id = 2, name = "Eggs", ingredients = listOf("egg", "oil"))
        )
        // When
        val meal= useCase.getMealHasNoEggs()
        //Then
        assertThat(meal.id).isEqualTo(1)
    }
    @Test
    fun `should return a different no egg meal when called for the second time`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "no Eggs1", ingredients = listOf("Tomato", "oil")),
            createMeal(id = 2, name = "no Eggs2", ingredients = listOf("Potato", "Cheese")),
            createMeal(id = 3, name = "no Eggs3", ingredients = listOf("onion", "meat"))
        )
        // When
        val meal1= useCase.getMealHasNoEggs()
        val meal2= useCase.getMealHasNoEggs()
        //Then
        assertThat(meal1).isNotEqualTo(meal2)
    }
    @Test
    fun `should throw MealNotFoundExceptions when there's no different no egg meal when called for the second time`() {
        // Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "no Eggs1", ingredients = listOf("Tomato", "oil")),
            createMeal(id = 2, name = "Eggs", ingredients = listOf("Potato", "Cheese","egg")),
            createMeal(id = 3, name = "Eggs2", ingredients = listOf("onion", "meat", "egg"))
        )
        // When
        useCase.getMealHasNoEggs()
        //Then
        assertThrows<MealNotFoundExceptions> { useCase.getMealHasNoEggs() }

    }

}