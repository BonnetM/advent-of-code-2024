package org.example.day3


class Corrupted {

    suspend fun compute(filename: String) {
        val line = readFile(filename)

        val mulRegex = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

        val res = mulRegex.findAll(line).sumOf {
            val a = it.groupValues[1].toInt()
            val b = it.groupValues[2].toInt()
            println("$a * $b")
            a * b
        }
        println("$res")
    }

    private fun readFile(filename: String): String {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader().readLines().joinToString()
    }
}