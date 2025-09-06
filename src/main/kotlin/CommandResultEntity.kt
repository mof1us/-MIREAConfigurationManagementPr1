package org.example

class CommandResultEntity(
    val isError: Boolean,
    val resultText: String,
    val afterCommand: () -> Unit
) {
    companion object {
        fun processString(text: String): String {
            val words = text.split(" ")
            var resultString = ""
            words.forEach { word ->
                if (word[0] == '$') {
                    resultString += System.getenv(word.substringAfter("$")) + " "
                } else {
                    resultString += "$word "
                }

            }
            return resultString
        }
    }
}