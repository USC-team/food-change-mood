package presentation

import domain.model.GuessResult
import domain.model.Meal
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetKetoMealUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import domain.usecase.createMeal
import domain.usecase.exceptions.InvalidChoiceException
import domain.usecase.exceptions.MealNotFoundExceptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.presentation.choices.ChoicesManager
import org.example.presentation.console_io.IMessagePrinter
import org.example.presentation.console_io.IReadManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ChoicesManagerTest {
    private val getGuessGameUseCase: GetGuessGameUseCase=mockk(relaxed = true)
    private val getSweetsWithNoEggsUseCase: GetSweetsWithNoEggsUseCase=mockk(relaxed = true)
    private val getEasyPreparedMealsUseCase: GetEasyPreparedMealsUseCase=mockk(relaxed = true)
    private val getKetoMealUseCase: GetKetoMealUseCase=mockk(relaxed = true)
    private val messagePrinter: IMessagePrinter=mockk(relaxed = true)
    private val readManager: IReadManager=mockk(relaxed = true)
    private lateinit var choicesManager: ChoicesManager

    @BeforeEach
    fun setUp() {
        choicesManager = ChoicesManager(
            getGuessGameUseCase,
            getSweetsWithNoEggsUseCase,
            getEasyPreparedMealsUseCase,
            getKetoMealUseCase,
            messagePrinter,
            readManager
        )
    }

    @Test
    fun `should call sayGoodBye when input is 0`() {
        //When
        choicesManager.chooseOption(0)
        //Then
        verify{messagePrinter.sayGoodBye()}
    }

    @Test
    fun `should throw InvalidChoiceException for invalid input`() {
        //When &Then
        assertThrows<InvalidChoiceException> { choicesManager.chooseOption(999) }
    }
    @Test
    fun `should explain choice and print meals when input is 4 and meals are available`() {
        // Given
        val mealList= listOf(
            createMeal(id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Smoothie", minutes = 5, nIngredients = 4, nSteps = 1)
        )
        every { getEasyPreparedMealsUseCase.getEasyPreparedMeals() } returns mealList
        // When
        choicesManager.chooseOption(4)
        //Then
        verify{messagePrinter.explainChoice(4)}
        mealList.forEach {
            verify{messagePrinter.printMealName(it)}
        }
    }

    @Test
    fun `should throw MealNotFoundExceptions when input is 4 and no meals are available`() {
        // Given
        val mealList= emptyList<Meal>()
        every { getEasyPreparedMealsUseCase.getEasyPreparedMeals() } returns mealList
        assertThrows<MealNotFoundExceptions> {
            // When
            choicesManager.chooseOption(4)
            //Then
            verify{messagePrinter.explainChoice(4)}}
    }
    @Test
    fun `should play guess game and succeed on first try`() {
        val meal = createMeal(id = 1, name = "Pizza", minutes = 20, nIngredients = 3, nSteps = 2)
        every { getGuessGameUseCase.getRandomMeal() } returns meal
        every { readManager.myReadInt() } returns 20
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 20) } returns GuessResult.Correct

        choicesManager.chooseOption(5)

        verify { messagePrinter.successMessage() }
        verify { messagePrinter.printMealName(meal) }
    }

    @Test
    fun `should play guess game and fail after 3 incorrect guesses`() {
        val meal = createMeal(id = 1, name = "Pizza", minutes = 20, nIngredients = 3, nSteps = 2)
        every { getGuessGameUseCase.getRandomMeal() } returns meal
        every { readManager.myReadInt() } returnsMany listOf(10, 15, 18)
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 10) } returns GuessResult.TooLow
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 15) } returns GuessResult.TooLow
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 18) } returns GuessResult.TooLow

        choicesManager.chooseOption(5)

        verify(exactly = 3) { messagePrinter.printMessage(match { it.contains("too low", ignoreCase = true) }) }
        verify { messagePrinter.wrongGuessMessage(meal) }
    }

    @Test
    fun `should show high then correct guess in second attempt`() {
        val meal = createMeal(id = 2, name = "Cake", minutes = 30, nIngredients = 5, nSteps = 3)
        every { getGuessGameUseCase.getRandomMeal() } returns meal
        every { readManager.myReadInt() } returnsMany listOf(40, 30)
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 40) } returns GuessResult.TooHigh
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 30) } returns GuessResult.Correct

        choicesManager.chooseOption(5)

        verify { messagePrinter.printMessage(match { it.contains("too high", ignoreCase = true) }) }
        verify { messagePrinter.successMessage() }
    }
    @Test
    fun `should repeat until user likes the no-eggs sweet`() {
        val meal = createMeal(id = 3, name = "Fruit Tart", minutes = 15, nIngredients = 4, nSteps = 3)
        every { getSweetsWithNoEggsUseCase.getMealHasNoEggs() } returns meal
        every { readManager.myReadLine() } returnsMany listOf("n", "y")

        choicesManager.chooseOption(6)

        verify(exactly = 2) { messagePrinter.printMealName(meal) }
        verify(exactly = 2) { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }

    @Test
    fun `should show meal immediately if user likes it the first time`() {
        val meal = createMeal(id = 4, name = "Oat Cookies", minutes = 12, nIngredients = 5, nSteps = 2)
        every { getSweetsWithNoEggsUseCase.getMealHasNoEggs() } returns meal
        every { readManager.myReadLine() } returns "Y"

        choicesManager.chooseOption(6)

        verify { messagePrinter.printMealName(meal) }
        verify { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }
    @Test
    fun `should keep suggesting keto meals until user likes it`() {
        val meal = createMeal(id = 5, name = "Keto Salad", minutes = 10, nIngredients = 5, nSteps = 2)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.myReadLine() } returnsMany listOf("n", "y")

        choicesManager.chooseOption(7)

        verify(exactly = 2) { messagePrinter.printMealName(meal) }
        verify(exactly = 2) { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }

    @Test
    fun `should show keto meal details immediately if liked`() {
        val meal = createMeal(id = 6, name = "Keto Bowl", minutes = 8, nIngredients = 4, nSteps = 1)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.myReadLine() } returns "Y"

        choicesManager.chooseOption(7)

        verify { messagePrinter.printMealName(meal) }
        verify { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }

}

