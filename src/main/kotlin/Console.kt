package org.example

import org.example.GUI.ConsoleGUI
import org.example.GUI.consolePrint
import org.example.GUI.printIndicator
import java.awt.EventQueue
import java.lang.System.exit
import java.util.Scanner
import kotlin.system.exitProcess

class Console {
    val scanner = Scanner(System.`in`)
    val consoleParams = ConsoleParams()
    val frame = ConsoleGUI("Console GUI interface")

    constructor() {
    }

    fun greeting() {
        consolePrint(frame, "Welcome to Mof1us GUI command line")

    }

    fun exec() {
        greeting()
        printIndicator(frame, { command -> parseCommand(command) })

    }

    fun sendResult(commandResult: CommandResultEntity) {
        consolePrint(frame, commandResult.resultText)
        commandResult.afterCommand()
        printIndicator(frame, { command -> parseCommand(command) })
    }

    fun parseCommand(command: String) {
        val result = when {
            command == "exit" -> CommandResultEntity(false, "off...", { exitProcess(0) })
            command.startsWith("echo") -> CommandResultEntity(
                false,
                CommandResultEntity.processString(command.substringAfter("echo ")),
                { })

            command == "ls" -> CommandResultEntity(false, "ls", {})
            command == "cd" -> CommandResultEntity(false, "cd [PATH]", {})
            command == "params" -> CommandResultEntity(false, consoleParams.toString(), {})
            command.startsWith("setp") -> {
                val flags = command.substringAfter("setp")
            }

            else -> CommandResultEntity(true, "Unknown command", {})
        } as CommandResultEntity
        sendResult(result)
    }
}

