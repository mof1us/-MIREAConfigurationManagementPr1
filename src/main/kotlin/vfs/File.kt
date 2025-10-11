package org.example.vfs

class FileVFS: VFSObject {
    private var content: String = ""

    constructor(name: String, content: String){
        setName(name)
        setContent(content)
    }

    fun getContent(): String = content
    fun setContent(content: String) {
        this.content = content
    }

    fun File(name: String, content: String) {
        setName(name)
        setContent(content)
    }

}