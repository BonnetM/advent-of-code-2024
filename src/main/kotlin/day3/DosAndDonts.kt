package org.example.day3


class DosAndDonts {

    suspend fun compute(filename: String) {
        val line = readFile(filename)

        val dosAndDonts = Regex("don't\\(\\).*?do\\(\\)")
        println("before: $line")
        val cleaned = dosAndDonts.replace(line, "")
        println("after: $cleaned")

        val mulRegex = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

        val res = mulRegex.findAll(cleaned).sumOf {
            val a = it.groupValues[1].toInt()
            val b = it.groupValues[2].toInt()
            a * b
        }
    }

    private fun readFile(filename: String): String {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader().readLines().joinToString()
    }
}