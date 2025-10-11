package org.example

import jdk.internal.org.jline.utils.Colors.s
import org.example.GUI.ConsoleGUI
import org.example.GUI.consolePrint
import org.example.GUI.printIndicator
import org.example.vfs.FileVFS
import org.example.vfs.Folder
import org.example.vfs.VFSObject
import org.example.vfs.parseVFSBasic
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
    var vfs: VFSObject? = Folder("");
    var currentBaseFolder = vfs
    fun greeting() {
        consolePrint(frame, "Welcome to Mof1us GUI command line")
    }

    fun initVFS(){
        consolePrint(frame, "Start initializating VFS ${consoleParams.getRealPath()}")
        try{
            val parsed = parseVFSBasic(File(consoleParams.getRealPath()).readText())
            parsed.forEach { node ->
                val pathList = node.path.split("/")
                var currentVFSFolder = vfs
                pathList.forEach {
                    if (it != "" && it != pathList.last()) {
                        var newFolder = (currentVFSFolder as Folder).getChildren(it)
                        if (newFolder == null){
                            newFolder = Folder(it)
                            (currentVFSFolder as Folder).addChildren(newFolder)
                        }
                        currentVFSFolder = newFolder


                    }
                    else if (it == pathList.last()){
                        (currentVFSFolder as Folder).addChildren(when(node.type){
                            "file" -> FileVFS(it, node.content)
                            "dir" -> Folder(it)
                            else -> throw Exception("Unknown folder type")
                        })
                    }
                }
            }
            val initMessage = (vfs as Folder).getChildren("motd")
            if (initMessage !== null) {
                consolePrint(frame, (initMessage as FileVFS).getContent())
            }
        }
        catch (e: Exception){
            consolePrint(frame, "error while creating vfs ${e.message}")
        } // make vfs
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
        } // make start sctipt

    }

    fun exec() {
        greeting()
        initVFS()
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

    fun parseCommand(command: String, isNextInput: Boolean = true) {
        val result = when {
            command == "exit" -> CommandResultEntity(false, "off...", { exitProcess(0) })
            command.startsWith("echo") -> CommandResultEntity(
                false,
                CommandResultEntity.processString(command.substringAfter("echo ")),
                { })

            command.startsWith("ls") -> CommandResultEntity(false, "ls", {
                if (!command.substringAfter("ls").contains("/")){
                    currentBaseFolder?.printTree(0, {s: String -> consolePrint(frame, s)})
                    return@CommandResultEntity
                }
                try{
                    var currentVFSFolder = vfs
                    val pathList = command.substringAfter("ls /").split("/")
                    pathList.forEach {
                        currentVFSFolder = (currentVFSFolder as Folder).getChildren(it)
                    }
                    currentVFSFolder?.printTree(0, {s: String -> consolePrint(frame, s)})
                }
                catch (e: Exception){
                    consolePrint(frame, "wrong parameters $e")
                }
            })
            command.startsWith("cd") -> CommandResultEntity(false, "cd", {
                try{
                    var currentVFSFolder = if (command.substringAfter("cd ")[0] == '/') vfs else currentBaseFolder
                    val pathList = command.substringAfter("cd ").split("/")
                    pathList.forEach {
                        if (it != ""){
                            currentVFSFolder = (currentVFSFolder as Folder).getChildren(it)
                        }

                    }
                    currentBaseFolder = currentVFSFolder
//                    currentVFSFolder?.printTree(0, {s: String -> consolePrint(frame, s)})
                }
                catch (e: Exception){
                    consolePrint(frame, "wrong parameters $e")
                }
            })
            command.startsWith("cat") -> CommandResultEntity(false, "cat", {
                try{
                    var currentVFSFolder = if (command.substringAfter("cat ")[0] == '/') vfs else currentBaseFolder
                    val pathList = command.substringAfter("cat ").split("/")
                    pathList.forEach {
                        if (it !== ""){
                            currentVFSFolder = (currentVFSFolder as Folder).getChildren(it)
                        }

                    }
                    consolePrint(frame, (currentVFSFolder as FileVFS).getContent())
//                    currentVFSFolder?.printTree(0, {s: String -> consolePrint(frame, s)})
                }
                catch (e: Exception){
                    consolePrint(frame, "wrong parameters ${e.message}")
                }
            })
            command == "params" -> CommandResultEntity(false, consoleParams.toString(), {})
            command.startsWith("setp") -> { val flags = command.substringAfter("setp") }

            else -> CommandResultEntity(true, "Unknown command", {})
        } as CommandResultEntity
        sendResult(result, isNextInput)
    }
}

