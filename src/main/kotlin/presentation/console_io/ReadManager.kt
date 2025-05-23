package org.example.presentation.console_io

object ReadManager: IReadManager {
    override fun myReadLine(): String {
        while (true) {
            try{
            return readln()
            }catch (e: Exception) {
                println("Please enter a valid input. Error: ${e.message}")
            }
        }
    }

    override fun myReadInt(): Int {
        while (true) {
            try {
                return readln().toInt()
            } catch (e: Exception) {
                println("Please enter a valid integer. Error: ${e.message}")
            }
        }
    }
}