package org.example.day9

class WholeFileDefragmentation {

    private data class MemoryBlock(val value: Long?, val size: Int)

    fun compute(filename: String) {
        val diskMap = readFile(filename)
        val explodedMemory = mutableListOf<Long?>()
        val blockMemory = mutableListOf<MemoryBlock>()
        diskMap.forEachIndexed { i, char ->
            val count = char.digitToInt()
            if (i.mod(2) == 0) {
                // File
                repeat(count) {
                    explodedMemory.add(i.div(2L))
                }
                if (count != 0)
                    blockMemory.add(MemoryBlock(i.div(2L), count))
            } else {
                // Free
                repeat(count) {
                    explodedMemory.add(null)
                }
                if (count != 0)
                    blockMemory.add(MemoryBlock(null, count))
            }
        }

//        prettyPrint(blockMemory)

        var indexToMove = blockMemory.size - 1

        while (indexToMove > 0) {
            //println("TRYING TO MOVE THE MEM[$indexToMove]=${blockMemory[indexToMove].value} - need space of ${blockMemory[indexToMove].size}")
            if (blockMemory[indexToMove].value != null) {
                //println("TRYING TO MOVE THE MEM[$indexToMove]=${blockMemory[indexToMove].value} - need space of ${blockMemory[indexToMove].size}")
                for (freeMemoryIndex in 0..<indexToMove) {
                    if (blockMemory[freeMemoryIndex].value == null) {
                        val sizeDelta = blockMemory[freeMemoryIndex].size - blockMemory[indexToMove].size
                        if (sizeDelta >= 0) {
                            val toMove = blockMemory.removeAt(indexToMove)
                            blockMemory.add(indexToMove, MemoryBlock(null, toMove.size))
                            blockMemory.removeAt(freeMemoryIndex)
                            blockMemory.add(freeMemoryIndex, toMove)
                            if (sizeDelta > 0) {
                                blockMemory.add(freeMemoryIndex + 1, MemoryBlock(null, sizeDelta))
                                indexToMove++
                            }
                            //prettyPrint(blockMemory)
                            break
                        }
                    }
                }
            }
            indexToMove--
        }

        //prettyPrint(blockMemory)

        val res = blockMemory.flatMap { block ->
            List(block.size) { block.value }
        }.mapIndexed { index, c ->
            if (c == null) 0L else index * c
        }.sum()

        println(res)
    }

    private fun prettyPrint(memoryBlocks: List<MemoryBlock>) {
        StringBuilder().apply {
            memoryBlocks.forEach { block ->
                if (block.value == null) repeat(block.size) { append(".") }
                else
                    repeat(block.size) { append("${block.value}") }

            }
        }.toString().let { println(it) }
    }


    private fun readFile(filename: String): String {
        val lines = this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
        return lines.first()
    }
}