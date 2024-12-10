package org.example.day5

class Reordering {

    data class Data(val rules: List<PageRule>, val updates: List<List<String>>)
    data class PageRule(val before: String, val after: String) {
        fun isRelevantForUpdate(update: List<String>): Boolean {
            return update.contains(before) && update.contains(after)
        }

        fun isSatisfiedForUpdate(update: List<String>): Boolean {
            return update.indexOf(before) < update.indexOf(after)
        }
    }

    fun compute(filename: String) {
        val (rules, updates) = readFile(filename)

        val res = updates.mapNotNull { update ->
            val relevantRules = rules.filter { it.isRelevantForUpdate(update) }
            val incorrect = relevantRules.any { !it.isSatisfiedForUpdate(update) }

            if (incorrect) {
                val shouldBeAfter = update.associateWith { page ->
                    relevantRules.filter { it.after == page }.map { it.before }.toMutableSet()
                }.toMutableMap()
                val newList = mutableListOf<String>()
                while (shouldBeAfter.isNotEmpty()) {
                    val freelyPlaceable = shouldBeAfter.filter { it.value.isEmpty() }.map { it.key }
                    if (freelyPlaceable.isEmpty()) {
                        throw IllegalStateException("free is empty")
                    }
                    freelyPlaceable.forEach { placed ->
                        newList.add(placed)
                        shouldBeAfter.remove(placed)
                        shouldBeAfter.forEach {
                            it.value.remove(placed)
                        }
                    }
                }
                newList[update.size / 2]
            } else {
                null
            }
        }.sumOf { it.toInt() }

        println("$res")

    }


    private fun readFile(filename: String): Data {
        val lines = this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
        val blankIndex = lines.indexOfFirst { it.isBlank() }
        val rules = lines.subList(0, blankIndex).map { line ->
            line.split("|").let { PageRule(it[0], it[1]) }
        }

        val updates: List<List<String>> = lines.subList(blankIndex + 1, lines.size).map { line ->
            line.split(",")
        }

        return Data(rules, updates)

    }

}