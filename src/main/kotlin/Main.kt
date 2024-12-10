package org.example

import kotlinx.coroutines.runBlocking
import org.example.day10.Rating
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val day = "10"
        val inputMode = true
        val filename = "/$day-${if (inputMode) "input" else "example"}.txt"
        println("Solving $filename")
        val time = measureTimeMillis {
            Rating().compute(filename)
        }
        println("Completed in $time ms")
    }
}