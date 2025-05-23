package org.example.presentation.console_io

import domain.model.Meal
import domain.model.Nutrition
import org.example.presentation.console_io.ConsoleColors

object MessagesPrinter: IMessagePrinter {

    override fun sayGoodBye() {
        println(
            ConsoleColors.MAGENTA_COLOR +
                    "Bye Bye! See you again!" +
                    ConsoleColors.RESET_COLOR
        )
    }
    override fun explainChoice(choice:Int) {
        when(choice) {
            4-> println(
                "Like a fun game, this feature suggests 10 random meals that are easy to prepare. \n" +
                        "A meal is considered easy if it requires 30 minutes or less, \n" +
                        "has 5 ingredients or fewer, and can be prepared in 6 steps or fewer."
            )
            5->println(
                    "we'll show a random meal name and you'll guess its preparation time.\n" +
                            " you have 3 attempts. After each attempt, we'll tell you whether the guessed time is correct,\n" +
                            " too low, or too high. If all attempts are incorrect, we'll show you the correct time."
                    )
            6->println(
                "if you are allergic to eggs, we'll suggest one sweet giving you the name and description" +
                        " that contains no eggs. you can either like it (to view full details) or dislike it (to get another egg-free sweet)."
            )
            7->println(
                "if you are following Keto Diet, we can suggest one meal that can be included in your diet." +
                        "you can either like it (to view full details) or dislike it (to get another Keto meal)."
            )
        }
    }
    override fun printMealName(meal: Meal){
        println("\tMeal Name: ${ConsoleColors.GREEN_COLOR} ${meal.name} ${ConsoleColors.RESET_COLOR}")
    }
    override fun wrongGuessMessage(meal: Meal){
        println("${ConsoleColors.RED_COLOR} Failed!\n Correct answer is ${meal.minutes}${ConsoleColors.RESET_COLOR}")
    }
    override fun askUserToEnter(label: String) {
        print("Please enter $label: ")
    }

    override fun successMessage() {
        println("${ConsoleColors.GREEN_COLOR}Excellent!${ConsoleColors.RESET_COLOR}")
    }

    override fun printMessage(message: String) {
        println(message)
    }
    override fun askUserIfLikedMeal() {
        println("Do you like this meal? if you like it, we'll show you the details,\n" +
                " if not, we'll suggest another one! (Y/N)")
    }

    override fun showMealsDetails(meal: Meal) {
        println(
            "Name:               ${meal.name}\n" +
                    "ID:                 ${meal.id}\n" +
                    "Minutes:            ${meal.minutes}\n" +
                    "Contributor ID:     ${meal.contributorId}\n" +
                    "Submitted:          ${meal.submitted}\n" +
                    "Tags:")
        meal.tags?.forEach { println("\t $it") }
        println(    "Nutrition:")
        printNutrients(meal)
        println(    "Number of Steps:    ${meal.nSteps}\n" +
                "Steps:")
        meal.steps?.forEach { println("\t $it  ") }
        println(    "Description:        ${meal.description}\n" +
                "Ingredients:")
        meal.ingredients?.forEach { println("\t $it  ") }
        println(
            "Ingredients Number: ${meal.nIngredients}\n"
        )
    }

    override fun greet() {
        println(
            ConsoleColors.MAGENTA_COLOR +
                    "***Welcome in USC Personal Finance Tracker***" +
                    ConsoleColors.RESET_COLOR
        )

    }

    override fun showOptions() {
        println(
            "you can choose one of the following:\n" +
                    //"1-  get a list of healthy fast food meals.\n" +
                    //"2-  meal search by name.\n" +
                    //"3-  Iraqi meals.\n" +
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

    private fun printNutrients(meal :Meal){
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
}