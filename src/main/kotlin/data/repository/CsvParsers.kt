package data.repository

object CsvParsers {
  /**
   * Turns "['a','b','c']" into List("a","b","c"),
   * or `"a,b,c"` (if un-bracketed) into List("a","b","c")
   */
  fun parseStringList(raw: String): List<String> =
    raw.trim().let {
      val stripped = it.removePrefix("[").removeSuffix("]")
      // split on commas not inside nested quotes
      stripped
        .split(',')
        .map { token -> token.trim().trim('\'','"') }
        .filter { it.isNotEmpty() }
    }

  /**
   * Turns "[1.0,2.5,3]" into List(1.0,2.5,3.0)
   */
  fun parseDoubleList(raw: String): List<Double> =
    raw.trim().removePrefix("[").removeSuffix("]")
      .split(',')
      .mapNotNull { it.trim().toDoubleOrNull() }
}
