package org.example.presentation

class FoodChangeMoodConsole() {

    fun start(){
        greeet()
        chooseOption()
    }
    fun chooseOption(){
        showOptions()
        val userChoice= getUserChoice()
        when (userChoice) {
            0->{
                sayGoodBye()
                return
            }
            1->{
                explainFirstChoice()

            }
            else->{
                println("Invalid Choice!")
            }
        }
        chooseOption()
    }
    fun greeet(){
        println(ConsoleColors.MAGENTA_COLOR+
                "***Welcome in USC Personal Finance Tracker***" +
                ConsoleColors.RESETCOLOR)
    }
    fun sayGoodBye(){
        println(ConsoleColors.MAGENTA_COLOR +
                "Bye Bye! See you again!" +
                ConsoleColors.RESETCOLOR)
    }
    fun explainFirstChoice(){
        println("you'll get a list of healthy fast food meals that can be prepared in 15 minutes" +
                " or less, with very low total fat, saturated fat, and carbohydrate values" +
                " compared to other meals in our list")
    }
    fun explainSecondChoice(){
        println("enter a country name, either in capital or small or even a mix. " +
                "don't worry about spelling mistakes! " +
                "and we will show all meals in our list related to that country")
    }
    fun explainThirdChoice(){
        println("a list of Iraqi meals will be shown. you'll see any meal related to Iraq in our list")
    }
    fun explainFourthChoice(){
        println("Like a fun game, this feature suggests 10 random meals that are easy to prepare. " +
                "A meal is considered easy if it requires 30 minutes or less, " +
                "has 5 ingredients or fewer, and can be prepared in 6 steps or fewer.")
    }
    fun explainFifthChoice(){
        println("we'll show a random meal name and you'll guess its preparation time." +
                " you have 3 attempts. After each attempt, we'll tell you whether the guessed time is correct," +
                " too low, or too high. If all attempts are incorrect, we'll show you the correct time.")
    }
    fun explainSixthChoice(){
        println("if you are allergic to eggs, we'll suggest one sweet giving you the name and description" +
                " that contains no eggs. you can either like it (to view full details) or dislike it (to get another egg-free sweet).")
    }
    fun explainSeventhChoice(){
        println("if you are following Keto Diet, we can suggest one meal that can be included in your diet." +
                "you can either like it (to view full details) or dislike it (to get another Keto meal).")
    }
    fun explainEighthChoice(){
        println("enter a date, and we'll show a list of meals that were added in that date")
    }
    fun explainNinthChoice(){
        println("enter a desired amount of calories and protein, " +
                "and we will give you a list of meals that match or approximate those values.")
    }
    fun explainTenthChoice(){
        println("enter a country name, and we'll return " +
                "up to 20 randomly ordered meals related to that country.")
    }
    fun explainEleventhChoice(){
        println("we'll Display a meal name and three ingredient options (one correct, two incorrect)." +
                " you'll guess once. A correct guess earns 1000 points; an incorrect guess ends the game. " +
                "The game also ends after 15 correct answers. we'll Display the final score at the end.")
    }
    fun explainTwelfthChoice() {
        println("for potato lovers! we'll Show a random list of 10 meals that include potatoes " +
                "in their ingredients.")
    }
    fun explainThirteenthChoice(){
        println("we'll Suggest a meal with more than 700 calories. you can either like it (to view full details)" +
                " or dislike it (to get another meal with high calories). ")
    }
    fun explainFourteenthChoice(){
        println("we'll Show a list of all seafood meals sorted by protein content, from highest to lowest." +
                " we'll display the rank (starting from 1), meal name, and protein amount.")
    }
    fun explainFifteenthChoice(){
        println("if u and A large group of friends are traveling to Italy," +
                " we'll show you a list of original Italian dishes, that are suitable for large groups.")
    }
    fun showOptions(){
        println("you can choose one of the following:" +
                "1-  get a list of healthy fast food meals.\n" +
                "2-  meal search by name.\n" +
                "3-  Iraqi meals.\n" +
                "4-  Easy Food Suggestion.\n" +
                "5-  Guess Game.\n" +
                "6-  Sweets with No Eggs.\n" +
                "7-  Keto Diet Meal Helper." +
                "8-  Search Foods by Add Date." +
                "9-  Gym Helper.\n" +
                "10- Explore Other Countries' Food Culture.\n" +
                "11- Ingredient Game.\n" +
                "12- I Love Potato.\n" +
                "13- So Thin Problem.\n" +
                "14- seafood meals.\n" +
                "15- traveling to Italy.\n" +
                "0-  Exit.\n")
    }
    fun getUserInput():String?{
        return readlnOrNull()
    }
    fun getUserChoice():Int?{
        return readlnOrNull()?.toIntOrNull()
    }
}