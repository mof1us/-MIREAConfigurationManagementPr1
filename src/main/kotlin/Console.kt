package org.example

import org.example.GUI.ConsoleGUI
import org.example.GUI.consolePrint
import org.example.GUI.printIndicator
import java.awt.EventQueue
import java.io.BufferedReader
import java.io.File
import java.lang.System.console
import java.lang.System.exit
import java.util.Scanner
import kotlin.system.exitProcess

class Console(
    val consoleParams: ConsoleParams
) {

    val frame = ConsoleGUI("Console GUI interface")

    fun greeting() {
        consolePrint(frame, "Welcome to Mof1us GUI command line")
    }

    fun execStartScript(){
        consolePrint(frame, "Start executing startup script ${consoleParams.getStartScript()}")
        try{
            File(consoleParams.getStartScript()).forEachLine {
                consolePrint(frame, "VFS@root:~$ ${it}")
                parseCommand(it, isNextInput=false)
            }
        }
        catch (e: Exception){
            consolePrint(frame, "error while opening startup script")
        }

    }

    fun exec() {
        greeting()
        execStartScript()



        printIndicator(frame, { command -> parseCommand(command) })

    }

    fun sendResult(commandResult: CommandResultEntity, isNextInput: Boolean = false) {
        commandResult.resultText.split("\n").forEach { line ->
            consolePrint(frame, line)
        }

        commandResult.afterCommand()
        if(isNextInput){
            printIndicator(frame, { command -> parseCommand(command) })
        }

    }

    fun parseCommand(command: String, isNextInput: Boolean = false) {
        val result = when {
            command == "exit" -> CommandResultEntity(false, "off...", { exitProcess(0) })
            command.startsWith("echo") -> CommandResultEntity(
                false,
                CommandResultEntity.processString(command.substringAfter("echo ")),
                { })

            command == "ls" -> CommandResultEntity(false, "ls", {})
            command == "cd" -> CommandResultEntity(false, "cd [PATH]", {})
            command == "params" -> CommandResultEntity(false, consoleParams.toString(), {})
            command.startsWith("setp") -> { val flags = command.substringAfter("setp") }

            else -> CommandResultEntity(true, "Unknown command", {})
        } as CommandResultEntity
        sendResult(result, isNextInput)
    }
}

