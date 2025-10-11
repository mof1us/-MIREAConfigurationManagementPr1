package org.example.vfs

class Folder : VFSObject {
    private var contains: MutableList<VFSObject> = mutableListOf()


    constructor (name: String) {
        setName(name)
    }

    fun addChildren(newChildren: VFSObject){
        contains.add(newChildren)
    }

    fun getChildren(name: String): VFSObject? {
        return contains.find { child -> child.getName() == name }

    }

    override fun printTree(spaces: Int, printFunction: (String) -> Unit){
        val character = "/"
        printFunction(" ".repeat(spaces) + character + getName())
        contains.forEach { child ->
            child.printTree(spaces + 1, printFunction)
        }
    }
}