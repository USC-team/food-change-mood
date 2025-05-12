package org.example.data.datasource

import java.io.File

class FoodCSVReader (val CSVFileName: String){
    val CSVFILE= File(CSVFileName)

    fun readFile(): List<String>{
        if(CSVFILE.exists())
            return CSVFILE.readLines().drop(1)//header row
        else throw Exception("File not found")
    }

}