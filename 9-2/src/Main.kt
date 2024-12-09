import java.io.File

fun main() {
    val inputData = File("src/input.txt").readText()
    val fileBlocks: MutableList<FileSystemEntry> = mutableListOf()

    for (i in 0..<inputData.count()) {
        val length = inputData[i].toString().toInt()

        if (length == 0) {
            continue
        }

        when (i % 2) {
            0 -> fileBlocks.add(FileSystemEntry(EntryType.FILE, length, i / 2))
            else -> fileBlocks.add(FileSystemEntry(EntryType.SPACE, length, (i - 1) / 2))
        }
    }

    val maxId = fileBlocks.last { it.FileType == EntryType.FILE }.ID
    for (id in maxId downTo 1) {
        val block = fileBlocks.first { it.ID == id && it.FileType == EntryType.FILE }
        val blockPosition = fileBlocks.indexOf(block)

        var spaceBefore: FileSystemEntry? = null
        var spaceAfter: FileSystemEntry? = null
        if (blockPosition - 1 >= 0 && fileBlocks[blockPosition - 1].FileType == EntryType.SPACE) {
            spaceBefore = fileBlocks[blockPosition - 1]
        }
        if (blockPosition + 1 < fileBlocks.count() && fileBlocks[blockPosition + 1].FileType == EntryType.SPACE) {
            spaceAfter = fileBlocks[blockPosition + 1]
        }

        for (j in 0..<blockPosition) {
            if (fileBlocks[j].FileType == EntryType.SPACE && fileBlocks[j].FileLength >= block.FileLength) {
                val space = fileBlocks[j]
                if (space == spaceBefore) {
                    fileBlocks[blockPosition - 1] = block
                    fileBlocks[blockPosition] = space
                    if (spaceAfter != null) {
                        space.FileLength += spaceAfter.FileLength
                        fileBlocks.remove(spaceAfter)
                    }
                } else {
                    space.FileLength -= block.FileLength
                    if (space.FileLength == 0) {
                        fileBlocks.remove(space)
                    }
                    fileBlocks.remove(block)
                    fileBlocks.add(j, block)

                    if (spaceBefore != null && spaceAfter != null) {
                        spaceBefore.FileLength += block.FileLength + spaceAfter.FileLength
                        fileBlocks.remove(spaceAfter)
                    } else if (spaceBefore != null) {
                        spaceBefore.FileLength += block.FileLength
                    } else if (spaceAfter != null) {
                        spaceAfter.FileLength += block.FileLength
                    } else {
                        if (space.FileLength == 0) {
                            fileBlocks.add(
                                blockPosition,
                                FileSystemEntry(EntryType.SPACE, block.FileLength, Int.MAX_VALUE - id)
                            )
                        } else {
                            fileBlocks.add(
                                blockPosition + 1,
                                FileSystemEntry(EntryType.SPACE, block.FileLength, Int.MAX_VALUE - id)
                            )
                        }
                    }
                }

                break
            }
        }
    }

    var sum: Long = 0
    var index: Long = 0
    for (block in fileBlocks) {
        for (j in 0..<block.FileLength) {
            if (block.FileType == EntryType.FILE) {
                sum += block.ID * index
            }
            index++
        }
    }

    println(sum)
}

class FileSystemEntry(val FileType: EntryType, var FileLength: Int, val ID: Int) {
    override fun toString(): String {
        return "${FileType} | ${ID} | ${FileLength}"
    }
}

enum class EntryType {
    FILE, SPACE
}