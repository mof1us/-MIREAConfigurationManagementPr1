package org.example

import java.lang.System.exit
import java.util.Scanner
import kotlin.system.exitProcess

class Console {
    val scanner = Scanner(System.`in`)


    fun greeting() {
        println(
            "\n" +
                    "███╗░░░███╗░█████╗░███████╗░░███╗░░██╗░░░██╗░██████╗\n" +
                    "████╗░████║██╔══██╗██╔════╝░████║░░██║░░░██║██╔════╝\n" +
                    "██╔████╔██║██║░░██║█████╗░░██╔██║░░██║░░░██║╚█████╗░\n" +
                    "██║╚██╔╝██║██║░░██║██╔══╝░░╚═╝██║░░██║░░░██║░╚═══██╗\n" +
                    "██║░╚═╝░██║╚█████╔╝██║░░░░░███████╗╚██████╔╝██████╔╝\n" +
                    "╚═╝░░░░░╚═╝░╚════╝░╚═╝░░░░░╚══════╝░╚═════╝░╚═════╝░\n" +
                    "\n" +
                    "░█████╗░░█████╗░███╗░░██╗░██████╗░█████╗░██╗░░░░░███████╗\n" +
                    "██╔══██╗██╔══██╗████╗░██║██╔════╝██╔══██╗██║░░░░░██╔════╝\n" +
                    "██║░░╚═╝██║░░██║██╔██╗██║╚█████╗░██║░░██║██║░░░░░█████╗░░\n" +
                    "██║░░██╗██║░░██║██║╚████║░╚═══██╗██║░░██║██║░░░░░██╔══╝░░\n" +
                    "╚█████╔╝╚█████╔╝██║░╚███║██████╔╝╚█████╔╝███████╗███████╗\n" +
                    "░╚════╝░░╚════╝░╚═╝░░╚══╝╚═════╝░░╚════╝░╚══════╝╚══════╝"
        );
    }

    fun exec() {
        greeting()
        var line = ""
        while (true) {
            print("VFS@root:~$ ")
            line = scanner.nextLine();
            val result = parseCommand(line)
            println(result.resultText)
            result.afterCommand()
        }
    }

    fun parseCommand(command: String): CommandResultEntity {
        when {
            command == "exit" -> return CommandResultEntity(false, "off...", { exitProcess(0) })
            command.startsWith("echo") -> return CommandResultEntity(
                false,
                CommandResultEntity.processString(command.substringAfter("echo ")),
                { })
            command == "ls" -> return CommandResultEntity(false, "ls", {})
            command == "cd" -> return CommandResultEntity(false, "cd [PATH]", {})
        }
        return CommandResultEntity(true, "Unknown command", {})
    }
}