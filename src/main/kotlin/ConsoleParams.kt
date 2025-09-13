package org.example

class ConsoleParams {
    private var realPath: String = "/"
    private var startScriptPath = "/"

    fun setRealPath(realPath: String) {
        this.realPath = realPath
    }

    fun setStartScriptPath(realPath: String) {
        this.startScriptPath = realPath
    }

    override fun toString(): String {
        return "Console params -->\nReal path for memory: ${realPath}\nStart script path: ${startScriptPath}"
    }
}