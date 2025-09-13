package org.example.GUI

import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridBagLayout
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.WindowConstants.EXIT_ON_CLOSE

class ConsoleGUI(title: String) : JFrame() {

    private val contentPanel = JPanel()

    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        setTitle(title)
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(1200, 800)
        setLocationRelativeTo(null)

        // Настраиваем основной контент панель
        contentPanel.layout = BoxLayout(contentPanel, BoxLayout.Y_AXIS)
        contentPanel.alignmentX = Component.LEFT_ALIGNMENT
        contentPanel.background = Color(0, 0, 0)

        // Создаем скролл панель
        val scrollPane = JScrollPane(contentPanel)
        scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        scrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED

        // Добавляем скролл панель в frame
        contentPane.add(scrollPane)
    }

    // Метод для добавления элементов
    fun addComponent(component: JComponent) {
        component.alignmentX = Component.LEFT_ALIGNMENT
        contentPanel.add(component)
        contentPanel.add(Box.createRigidArea(Dimension(0, 0))) // Отступ между элементами
        contentPanel.revalidate()
        contentPanel.repaint()
    }
}
