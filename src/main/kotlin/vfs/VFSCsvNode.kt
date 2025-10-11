package org.example.vfs

data class VFSNode(
    val type: String,
    val path: String,
    val content: String,
    val size: Int,
    val encoding: String = "text"
)

fun parseVFSBasic(csvContent: String): List<VFSNode> {
    val lines = csvContent.trim().lines()
    if (lines.isEmpty()) return emptyList()

    return lines.drop(1).mapNotNull { line ->
        try {
            val values = parseCSVLine(line)
            // Проверяем, что есть минимум 4 значения (type, path, content, size)
            if (values.size < 4) {
                println("Warning: Skipping invalid line - not enough values: $line")
                return@mapNotNull null
            }

            VFSNode(
                type = values[0],
                path = values[1],
                content = values[2],
                size = values[3].toIntOrNull() ?: 0,
                encoding = values.getOrNull(4) ?: "text"
            )
        } catch (e: Exception) {
            println("Error parsing line: $line - ${e.message}")
            null
        }
    }
}

fun parseCSVLine(line: String): List<String> {
    val result = mutableListOf<String>()
    var current = StringBuilder()
    var inQuotes = false

    for (char in line) {
        when {
            char == '"' -> inQuotes = !inQuotes
            char == ',' && !inQuotes -> {
                result.add(current.toString())
                current = StringBuilder()
            }
            else -> current.append(char)
        }
    }
    result.add(current.toString())
    return result
}

// Альтернативная версия с более строгой валидацией
fun parseVFSWithValidation(csvContent: String): List<VFSNode> {
    val lines = csvContent.trim().lines()
    if (lines.isEmpty()) return emptyList()

    val headers = lines.first().split(",")
    println("CSV Headers: $headers")

    return lines.drop(1).mapIndexedNotNull { index, line ->
        try {
            val values = parseCSVLine(line)
            println("Line ${index + 2}: $values")

            // Проверяем минимальное количество полей
            if (values.size < 4) {
                println("Warning: Line ${index + 2} has only ${values.size} values, expected at least 4")
                return@mapIndexedNotNull null
            }

            VFSNode(
                type = values[0].trim(),
                path = values[1].trim(),
                content = values[2].trim(),
                size = values[3].trim().toIntOrNull() ?: 0,
                encoding = values.getOrNull(4)?.trim() ?: "text"
            )
        } catch (e: Exception) {
            println("Error parsing line ${index + 2}: '$line' - ${e.message}")
            null
        }
    }
}