package org.example.presentation

import domain.model.Meal
import domain.model.Nutrition
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetKetoMealUseCase
import org.example.domain.model.GuessResult
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetHealthyMealsUseCase
import org.example.domain.usecase.GetSpecialIraqMealsUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase

class FoodChangeMoodConsole(
    private val getGuessGameUseCase: GetGuessGameUseCase,
    private val getSweetsWithNoEggsUseCase: GetSweetsWithNoEggsUseCase,
    private val getEasyPreparedMealsUseCase: GetEasyPreparedMealsUseCase,
    private val getKetoMealUseCase: GetKetoMealUseCase,
    private val getSpecialIraqMealsUseCase: GetSpecialIraqMealsUseCase,
    private val getHealthyMealsUseCase: GetHealthyMealsUseCase
) {

    fun start() {
        greet()
        chooseOption()
    }

    private fun chooseOption() {
        showOptions()
        val userChoice = getUserChoice()
        when (userChoice) {
            0 -> {
                sayGoodBye()
                return
            }

            1 -> {
                explainFirstChoice()
                getHealthyAndFastMeals()
            }

            3 -> {
                explainThirdChoice()
                getIraqMeals()
            }

            4 -> {
                explainFourthChoice()
                easyPrepareMeals()
            }

            5 -> {
                explainFifthChoice()
                guessGame()
            }

            6 -> {
                explainSixthChoice()
                noEggsSweet()
            }

            7 -> {
                explainSeventhChoice()
                ketoMeal()
            }

            else -> {
                println(
                    "${ConsoleColors.RED_COLOR}Invalid Choice!${ConsoleColors.RESET_COLOR}\n" +
                            "We'll support other features in the future!"
                )
            }
        }
        chooseOption()
    }

    private fun printNutrients(meal: Meal) {
        meal.nutrition?.let { showNutrientsDetails(it) }
    }

    private fun showNutrientsDetails(nutrients: Nutrition) {
        println(
            "\tCalories:      ${nutrients.calories}\n" +
                    "\tTotal Fat:     ${nutrients.totalFat}\n" +
                    "\tSugar:         ${nutrients.sugar}\n" +
                    "\tSodium:        ${nutrients.sodium}\n" +
                    "\tProtein:       ${nutrients.protein}\n" +
                    "\tSaturated Fat: ${nutrients.saturatedFat}\n" +
                    "\tCarbohydrates: ${nutrients.carbohydrates}"
        )
    }

    private fun greet() {
        println(
            ConsoleColors.MAGENTA_COLOR +
                    "***Welcome in USC Personal Finance Tracker***" +
                    ConsoleColors.RESET_COLOR
        )
    }

    private fun getHealthyAndFastMeals() {
        println("\t Meal Name :${getHealthyMealsUseCase.getHealthyQuickMealsBelowAverage()}")
    }

    private fun getIraqMeals() {
        println("\t Meal Name :${getSpecialIraqMealsUseCase.getSpecialIraqMeals()}")
    }

    private fun easyPrepareMeals() {
        getEasyPreparedMealsUseCase.getEasyPreparedMeals().forEach {
            println("\tMeal Name: ${ConsoleColors.GREEN_COLOR} ${it.name} ${ConsoleColors.RESET_COLOR}")
        }

    }

    private fun ketoMeal() {
        val meal = getKetoMealUseCase.getKetoMeal()
        println("Meal Name:${ConsoleColors.GREEN_COLOR}  ${meal.name} ${ConsoleColors.RESET_COLOR}")
        askUserIfLikedMeal()
        if (didYouLikeIt()) showMealsDetails(meal)
        else ketoMeal()
    }

    private fun guessGame() {
        val meal = getGuessGameUseCase.getRandomMeal()
        println("Meal Name:${ConsoleColors.GREEN_COLOR}  ${meal.name} ${ConsoleColors.RESET_COLOR}")
        if (!isCorrectGuess(meal = meal)) {
            println("${ConsoleColors.RED_COLOR} Failed!\n Correct answer is ${meal.minutes}${ConsoleColors.RESET_COLOR}")
        }
    }

    private fun isCorrectGuess(tries: Int = 3, meal: Meal): Boolean {
        if (tries > 0) {
            askUserToEnter("Guess Minutes")
            val guessResult = getGuessGameUseCase.isGuessCorrectHighOrLow(meal, getUserChoice())
            when (guessResult) {
                GuessResult.Correct -> {
                    println("${ConsoleColors.GREEN_COLOR}Excellent!${ConsoleColors.RESET_COLOR}")
                    return true
                }

                GuessResult.TooHigh -> {
                    println("You are Wrong! It's too high!")
                    isCorrectGuess(tries - 1, meal)
                }

                GuessResult.TooLow -> {
                    println("You are Wrong! It's too low!")
                    isCorrectGuess(tries - 1, meal)
                }
            }
        }
        return false
    }

    private fun noEggsSweet() {
        val meal = getSweetsWithNoEggsUseCase.getMealHasNoEggs()
        println("Meal Name:${ConsoleColors.GREEN_COLOR}  ${meal.name} ${ConsoleColors.RESET_COLOR}")
        askUserIfLikedMeal()
        if (didYouLikeIt()) showMealsDetails(meal)
        else noEggsSweet()
    }

    private fun didYouLikeIt(): Boolean {
        if (getUserInput().equals("y", ignoreCase = true))
            return true
        return false
    }

    private fun askUserIfLikedMeal() {
        println(
            "Do you like this meal? if you like it, we'll show you the details,\n" +
                    " if not, we'll suggest another one! (Y/N)"
        )
    }

    private fun showMealsDetails(meal: Meal) {
        println(
            "Name:               ${meal.name}\n" +
                    "ID:                 ${meal.id}\n" +
                    "Minutes:            ${meal.minutes}\n" +
                    "Contributor ID:     ${meal.contributorId}\n" +
                    "Submitted:          ${meal.submitted}\n" +
                    "Tags:"
        )
        meal.tags?.forEach { println("\t $it") }
        println("Nutrition:")
        printNutrients(meal)
        println(
            "Number of Steps:    ${meal.nSteps}\n" +
                    "Steps:"
        )
        meal.steps?.forEach { println("\t $it  ") }
        println(
            "Description:        ${meal.description}\n" +
                    "Ingredients:"
        )
        meal.ingredients?.forEach { println("\t $it  ") }
        println(
            "Ingredients Number: ${meal.nIngredients}\n"
        )
    }

    private fun sayGoodBye() {
        println(
            ConsoleColors.MAGENTA_COLOR +
                    "Bye Bye! See you again!" +
                    ConsoleColors.RESET_COLOR
        )
    }

    private fun explainFirstChoice() {
        println(
            "you'll get a list of healthy fast food meals that can be prepared in 15 minutes" +
                    " or less, with very low total fat, saturated fat, and carbohydrate values" +
                    " compared to other meals in our list"
        )
    }

    private fun explainThirdChoice() {
        println("a list of Iraqi meals will be shown. you'll see any meal related to Iraq in our list")
    }

    private fun explainFourthChoice() {
        println(
            "Like a fun game, this feature suggests 10 random meals that are easy to prepare. \n" +
                    "A meal is considered easy if it requires 30 minutes or less, \n" +
                    "has 5 ingredients or fewer, and can be prepared in 6 steps or fewer."
        )
    }

    private fun explainFifthChoice() {
        println(
            "we'll show a random meal name and you'll guess its preparation time.\n" +
                    " you have 3 attempts. After each attempt, we'll tell you whether the guessed time is correct,\n" +
                    " too low, or too high. If all attempts are incorrect, we'll show you the correct time."
        )
    }

    private fun explainSixthChoice() {
        println(
            "if you are allergic to eggs, we'll suggest one sweet giving you the name and description" +
                    " that contains no eggs. you can either like it (to view full details) or dislike it (to get another egg-free sweet)."
        )
    }

    private fun explainSeventhChoice() {
        println(
            "if you are following Keto Diet, we can suggest one meal that can be included in your diet." +
                    "you can either like it (to view full details) or dislike it (to get another Keto meal)."
        )
    }

    private fun showOptions() {
        println(
            "you can choose one of the following:" +
                    "1-  get a list of healthy fast food meals.\n" +
                    //"2-  meal search by name.\n" +
                    "3-  Iraqi meals.\n" +
                    "4-  Easy Food Suggestion.\n" +
                    "5-  Guess Game.\n" +
                    "6-  Sweets with No Eggs.\n" +
                    "7-  Keto Diet Meal Helper." +
                    // "8-  Search Foods by Add Date." +
                    // "9-  Gym Helper.\n" +
                    // "10- Explore Other Countries' Food Culture.\n" +
                    //"11- Ingredient Game.\n" +
                    //"12- I Love Potato.\n" +
                    //"13- So Thin Problem.\n" +
                    //"14- seafood meals.\n" +
                    //"15- traveling to Italy.\n" +
                    "0-  Exit.\n"
        )
    }

    private fun getUserInput(): String {
        return readlnOrNull() ?: throw Exception("Can't be null")
    }

    private fun getUserChoice(): Int {
        while (true) {
            askUserToEnter("Choice")
            val input = readlnOrNull()
            val number = input?.toIntOrNull()

            if (number != null) {
                return number
            } else {
                println("Invalid number. Please try again.")
            }
        }
    }

    private fun askUserToEnter(thingToEnter: String) {
        println("Enter $thingToEnter:")
    }
}
