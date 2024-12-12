package org.example

import kotlinx.coroutines.runBlocking
import org.example.day11.BlinkingALot
import org.example.day12.AreaAndPerimeter
import org.example.day12.AreaAndSides
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        // 853581 too low
        val day = "12"
        val inputMode = true
        val filename = "/$day-${if (inputMode) "input" else "example"}.txt"
        println("Solving $filename")
        val time = measureTimeMillis {
            AreaAndSides().compute(filename)
        }
        println("Completed in $time ms")
    }
}