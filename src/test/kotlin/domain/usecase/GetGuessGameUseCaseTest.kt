package domain.usecase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.model.GuessResult
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetGuessGameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test


class GetGuessGameUseCaseTest {
    private val repository: MealsRepository = mockk(relaxed = true)
    private lateinit var useCase: GetGuessGameUseCase

    @BeforeEach
    fun setup() {
        useCase = GetGuessGameUseCase(repository)
    }
    @Test
    fun `isGuessCorrectHighOrLow should Correct true when a correct guess entered for a given meal`(){
        //Given
        val meal= createMeal(id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2)
        val guessMinutes =10
        //When
        val guessResult= useCase.isGuessCorrectHighOrLow(meal,guessMinutes)
        //Then
        assertThat(guessResult).isEqualTo(GuessResult.Correct)
    }

    @Test
    fun `isGuessCorrectHighOrLow should return TooHigh when a guess is greater than correct minutes for a given meal`(){
        //Given
        val meal= createMeal(id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2)
        val guessMinutes =100
        //When
        val guessResult= useCase.isGuessCorrectHighOrLow(meal,guessMinutes)
        //Then
        assertThat(guessResult).isEqualTo(GuessResult.TooHigh)
    }
    @Test
    fun `isGuessCorrectHighOrLow should return TooLow when a guess is less than correct minutes for a given meal`(){
        //Given
        val meal= createMeal(id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2)
        val guessMinutes =1
        //When
        val guessResult= useCase.isGuessCorrectHighOrLow(meal,guessMinutes)
        //Then
        assertThat(guessResult).isEqualTo(GuessResult.TooLow)
    }
    @Test
    fun `isGuessCorrectHighOrLow should throw an exception when a the meal has no minutes`(){
        //Given
        val meal= createMeal(id = 1, name = "Salad", nIngredients = 3, nSteps = 2)
        val guessMinutes =1
        //When &&Then
        assertThrows<Exception>{useCase.isGuessCorrectHighOrLow(meal,guessMinutes) }
    }
    @Test
    fun `getRandomMeal should return a meal when there are meals in the list`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", minutes = 5, nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Pizza", minutes = 55, nIngredients = 5, nSteps = 6))
        //When
        val meal=useCase.getRandomMeal()
        //Then
        assertThat(meal).isNotNull()
    }
    @Test
    fun `getRandomMeal should return a meal that has minutes when there are meals in the list that have no minutes`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", minutes = 5, nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Pizza", nIngredients = 5, nSteps = 6))
        //When
        val meal=useCase.getRandomMeal()
        //Then
        assertThat(meal.minutes).isNotNull()
    }

    @Test
    fun `getRandomMeal should throw an exception when meal list is empty`(){
        //Given
        every { repository.getAllMeals() } returns emptyList()
        //When &Then
        assertThrows<Exception> { useCase.getRandomMeal() }
    }
    @Test
    fun `getRandomMeal should throw an exception when meal list only contains meals that have no minutes`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Pizza", nIngredients = 5, nSteps = 6))
        //When &Then
        assertThrows<Exception> { useCase.getRandomMeal() }
    }


}