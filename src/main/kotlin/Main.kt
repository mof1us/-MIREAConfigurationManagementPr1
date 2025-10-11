package org.example

import org.example.GUI.ConsoleGUI
import org.example.GUI.consolePrint
import java.awt.Color
import java.awt.EventQueue
import java.awt.FlowLayout
import java.awt.Label
import java.awt.SystemColor.text
import javax.swing.*;


//private fun createAndShowGUI() {
//
//    consolePrint(frame, "Hello")
//
//}

//  ./gradlew run --args="--path=somePath --script=C:\Users\anton\Documents\Учеба\3 семестр\Конфигурационка\start.sh"
fun main(args: Array<String>) {
    val consoleParams = ConsoleParams()
    args.forEach { arg ->
        if (arg.startsWith("--path=")) {
            val path = arg.substringAfter("=")
            consoleParams.setRealPath(path)
        }
        else if (arg.startsWith("--script=")) {
            val scriptPath = arg.substringAfter("=")
            consoleParams.setStartScriptPath(scriptPath)
        }
    }
    val console = Console(consoleParams=consoleParams);
    console.exec()
}