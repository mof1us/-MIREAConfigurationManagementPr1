package org.example.vfs


open class VFSObject(){
    private var name: String? = null
    private var type: String? = null

    fun getName(): String? = name
    fun getType(): String? = type
    fun setName(name: String?) {
        this.name = name
    }
    fun setType(type: String?) {
        this.type = type
    }

    open fun printTree(spaces: Int, printFunction: (String) -> Unit){
        val character = "-"
        printFunction(" ".repeat(spaces) + character + getName())
    }
}