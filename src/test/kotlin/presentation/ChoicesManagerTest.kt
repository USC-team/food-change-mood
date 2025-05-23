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
import io.mockk.verifySequence
import org.example.presentation.choices.ConsoleChoicesManager
import org.example.presentation.console_io.MessagePrinter
import org.example.presentation.console_io.ReadManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ChoicesManagerTest {
    private val getGuessGameUseCase: GetGuessGameUseCase = mockk(relaxed = true)
    private val getSweetsWithNoEggsUseCase: GetSweetsWithNoEggsUseCase = mockk(relaxed = true)
    private val getEasyPreparedMealsUseCase: GetEasyPreparedMealsUseCase = mockk(relaxed = true)
    private val getKetoMealUseCase: GetKetoMealUseCase = mockk(relaxed = true)
    private val messagePrinter: MessagePrinter = mockk(relaxed = true)
    private val readManager: ReadManager = mockk(relaxed = true)
    private lateinit var consoleChoicesManager: ConsoleChoicesManager

    @BeforeEach
    fun setUp() {
        consoleChoicesManager = ConsoleChoicesManager(
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
        //Given
        val choice=0
        //When
        consoleChoicesManager.chooseOption(choice)
        //Then
        verify { messagePrinter.sayGoodBye() }
    }

    @Test
    fun `should throw InvalidChoiceException for invalid input`() {
        //Given
        val choice=999
        //When &Then
        assertThrows<InvalidChoiceException> { consoleChoicesManager.chooseOption(choice) }
    }

    @Test
    fun `should explain choice and print meals when input is 4 and meals are available`() {
        // Given
        val mealList = listOf(
            createMeal(id = 1, name = "Salad", minutes = 10, nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Smoothie", minutes = 5, nIngredients = 4, nSteps = 1)
        )
        every { getEasyPreparedMealsUseCase.getEasyPreparedMeals() } returns mealList
        val choice=4
        // When
        consoleChoicesManager.chooseOption(choice)
        //Then
        verify { messagePrinter.explainChoice(any()) }
        mealList.forEach {
            verify { messagePrinter.printMealName(it) }
        }
    }

    @Test
    fun `should throw MealNotFoundExceptions when input is 4 and no meals are available`() {
        // Given
        val mealList = emptyList<Meal>()
        every { getEasyPreparedMealsUseCase.getEasyPreparedMeals() } returns mealList
        val choice=4
        assertThrows<MealNotFoundExceptions> {
            // When
            consoleChoicesManager.chooseOption(choice)
            //Then
            verify { messagePrinter.explainChoice(any()) }
        }
    }

    @Test
    fun `should play guess game and succeed on first try`() {
        val meal = createMeal(id = 1, name = "Pizza", minutes = 20, nIngredients = 3, nSteps = 2)
        every { getGuessGameUseCase.getRandomMeal() } returns meal
        every { readManager.readInt() } returns 20
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 20) } returns GuessResult.Correct
        val choice=5
        consoleChoicesManager.chooseOption(choice)

        verify {
            messagePrinter.printMealName(meal)}
        verify{
            messagePrinter.successMessage()}

    }

    @Test
    fun `should play guess game and fail after 3 incorrect guesses`() {
        val meal = createMeal(id = 1, name = "Pizza", minutes = 20, nIngredients = 3, nSteps = 2)
        every { getGuessGameUseCase.getRandomMeal() } returns meal
        every { readManager.readInt() } returnsMany listOf(10, 15, 18)
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 10) } returns GuessResult.TooLow
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 15) } returns GuessResult.TooLow
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 18) } returns GuessResult.TooLow
        val choice=5
        consoleChoicesManager.chooseOption(choice)

        verify(exactly = 3) { messagePrinter.printMessage(match { it.contains("too low", ignoreCase = true) }) }
        verify { messagePrinter.wrongGuessMessage(meal) }
    }

    @Test
    fun `should show high then correct guess in second attempt`() {
        val meal = createMeal(id = 2, name = "Cake", minutes = 30, nIngredients = 5, nSteps = 3)
        every { getGuessGameUseCase.getRandomMeal() } returns meal
        every { readManager.readInt() } returnsMany listOf(40, 30)
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 40) } returns GuessResult.TooHigh
        every { getGuessGameUseCase.isGuessCorrectHighOrLow(meal, 30) } returns GuessResult.Correct
        val choice=5
        consoleChoicesManager.chooseOption(choice)

        verify { messagePrinter.printMessage(match { it.contains("too high", ignoreCase = true) }) }
        verify { messagePrinter.successMessage() }
    }

    @Test
    fun `should repeat until user likes the no-eggs sweet`() {
        val meal = createMeal(id = 3, name = "Fruit Tart", minutes = 15, nIngredients = 4, nSteps = 3)
        every { getSweetsWithNoEggsUseCase.getMealHasNoEggs() } returns meal
        every { readManager.readLine() } returnsMany listOf("n", "y")
        val choice=6
        consoleChoicesManager.chooseOption(choice)

        verify(exactly = 2) { messagePrinter.printMealName(meal) }
        verify(exactly = 2) { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }

    @Test
    fun `should show meal immediately if user likes it the first time`() {
        val meal = createMeal(id = 4, name = "Oat Cookies", minutes = 12, nIngredients = 5, nSteps = 2)
        every { getSweetsWithNoEggsUseCase.getMealHasNoEggs() } returns meal
        every { readManager.readLine() } returns "Y"
        val choice=6
        consoleChoicesManager.chooseOption(choice)

        verify { messagePrinter.printMealName(meal) }
        verify { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }

    @Test
    fun `should keep suggesting keto meals when user didn't like the first one`() {
        val meal = createMeal(id = 5, name = "Keto Salad", minutes = 10, nIngredients = 5, nSteps = 2)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.readLine() } returnsMany listOf("n", "y")
        val choice=7
        consoleChoicesManager.chooseOption(choice)

        verify(exactly = 2) { messagePrinter.printMealName(meal) }
        verify(exactly = 2) { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }
    @Test
    fun `should keep suggesting keto meals until user likes it`() {
        val meal = createMeal(id = 5, name = "Keto Salad", minutes = 10, nIngredients = 5, nSteps = 2)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.readLine() } returnsMany listOf("n", "y")
        val choice=7
        consoleChoicesManager.chooseOption(choice)

        verify(exactly = 2) { messagePrinter.printMealName(meal) }
        verify(exactly = 2) { messagePrinter.askUserIfLikedMeal() }
        verify { messagePrinter.showMealsDetails(meal) }
    }

    @Test
    fun `should show keto meal name when choosing it from list`() {
        //Given
        val meal = createMeal(id = 6, name = "Keto Bowl", minutes = 8, nIngredients = 4, nSteps = 1)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.readLine() } returns "Y"
        val choice=7
        //When
        consoleChoicesManager.chooseOption(choice)
        //Then
        verify { messagePrinter.printMealName(meal) }
    }
    @Test
    fun `should ask user if he liked a meal to show its details when in keto meal`() {
        //Given
        val meal = createMeal(id = 6, name = "Keto Bowl", minutes = 8, nIngredients = 4, nSteps = 1)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.readLine() } returns "Y"
        val choice=7
        //When
        consoleChoicesManager.chooseOption(choice)
        //Then
        verify { messagePrinter.askUserIfLikedMeal() }
    }
    @Test
    fun `should print meals details when user likes it`() {
        //Given
        val meal = createMeal(id = 6, name = "Keto Bowl", minutes = 8, nIngredients = 4, nSteps = 1)
        every { getKetoMealUseCase.getKetoMeal() } returns meal
        every { readManager.readLine() } returns "Y"
        val choice=7
        //When
        consoleChoicesManager.chooseOption(choice)
        //Then
        verify { messagePrinter.showMealsDetails(meal) }
    }

}

