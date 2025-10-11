package org.example.GUI

import org.example.Console
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.Label
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.SwingUtilities
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun consolePrint(frame: ConsoleGUI, text: String) {
    text.split("\\n").forEach {
        frame.contentPane.background = Color(0, 0, 0)
        frame.isVisible = true
        val l = JTextArea(it)
        l.foreground = Color(255, 255, 255)
        l.background = Color(0, 0, 0)
        l.font = Font("Monospaced", Font.BOLD, 20)
        l.isEditable = false
        l.preferredSize = Dimension(l.preferredSize.width, 30)

        l.lineWrap = true
        l.wrapStyleWord = true
        l.preferredSize = Dimension(l.preferredSize.width, l.preferredSize.height)
        l.maximumSize = Dimension(Integer.MAX_VALUE, l.preferredSize.height) // Фиксируем высоту
        frame.addComponent(l)
    }

}



fun printIndicator(frame: ConsoleGUI, commandHandler: (String) -> Unit) {
    frame.contentPane.background = Color(0, 0, 0)
    frame.isVisible = true

    val panel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
    panel.background = Color(0, 0, 0)
    panel.isOpaque = true

    val inputInvite = JTextArea("VFS@root:~$")
    inputInvite.foreground = Color(255, 255, 255)
    inputInvite.background = Color(0, 0, 0)
    inputInvite.font = Font("Monospaced", Font.BOLD, 20)
    inputInvite.isEditable = false
    inputInvite.lineWrap = true
    inputInvite.wrapStyleWord = true
    val metrics = inputInvite.getFontMetrics(inputInvite.font)
    val textWidth = metrics.stringWidth("VFS@root:~ $")
    inputInvite.preferredSize = Dimension(textWidth, 30)
    inputInvite.minimumSize = Dimension(textWidth, 30)
    inputInvite.maximumSize = Dimension(textWidth, 30)


    val input = JTextArea("")
    input.foreground = Color(255, 255, 255)
    input.background = Color(0, 0, 0)
    input.font = Font("Monospaced", Font.BOLD, 20)
    input.isEditable = true
    input.lineWrap = true
    input.wrapStyleWord = true
    input.preferredSize = Dimension(10000, 30)
    input.maximumSize = Dimension(Integer.MAX_VALUE, 30)
    input.addKeyListener(object : KeyAdapter() {
        override fun keyPressed(e: KeyEvent) {
            if (e.keyCode == KeyEvent.VK_ENTER) {
                e.consume()
                val text = input.text.trim()
                commandHandler(text)
                input.isEditable = false
                input.removeKeyListener(this)
            }
        }
    })
    SwingUtilities.invokeLater {
        input.isFocusable = true
        input.requestFocusInWindow()
        input.caretPosition = input.text.length
    }


    panel.add(inputInvite)
    panel.add(input)

    panel.preferredSize = Dimension(panel.preferredSize.width, 40)
    panel.minimumSize = Dimension(panel.preferredSize.width, 40)
    panel.maximumSize = Dimension(Integer.MAX_VALUE, 40)

    frame.addComponent(panel)
}