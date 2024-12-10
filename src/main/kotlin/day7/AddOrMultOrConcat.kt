package org.example.day7

import kotlinx.coroutines.coroutineScope

class AddOrMultOrConcat {

    data class Equation(val target: Long, val values: List<Long>)

    suspend fun compute(filename: String) {
        val equations = readFile(filename)
        coroutineScope {
            val res = equations.map {
                if (recurs(it.target, it.values.first(), it.values.subList(1, it.values.size), "")) {
                    it.target
                } else 0
            }.sum()

            println("Result: $res")
        }
    }

    private fun readFile(filename: String): List<Equation> {
        val lines = this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()

        return lines.map {
            val splitted = it.split(":")
            val target = splitted[0].toLong()
            val values = splitted[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
            Equation(target, values)
        }

    }

    private fun recurs(target: Long, acc: Long, list: List<Long>, path: String): Boolean {
        return if (list.isEmpty()) {
            if (target == acc) {
                true
            } else {
                false
            }
        } else {
            if (acc > target) {
                false
            } else {
                recurs(target, acc + list.first(), list.subList(1, list.size), "$path ${list.first()} +") ||
                        recurs(target, acc * list.first(), list.subList(1, list.size), "$path ${list.first()} *") ||
                        recurs(target,(acc.toString() + list.first().toString()).toLong(), list.subList(1, list.size), "$path ${list.first()} *")
            }
        }
    }
}