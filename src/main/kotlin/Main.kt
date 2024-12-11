package org.example

import kotlinx.coroutines.runBlocking
import org.example.day11.BlinkingALot
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val day = "11"
        val inputMode = true
        val filename = "/$day-${if (inputMode) "input" else "example"}.txt"
        println("Solving $filename")
        val time = measureTimeMillis {
            BlinkingALot().compute(filename)
        }
        println("Completed in $time ms")
    }
}