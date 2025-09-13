package command

import service.expandPath

fun cd(directory: String = "") {
    when (val expandedPath = expandPath(directory)) {
        "", " ", "." -> {}
        "/", "~" -> { State.navStack.add("/") }
        "-" -> {
            if (State.navStack.size > 1) {
                State.navStack.add(State.navStack[State.navStack.size - 2])
            }
        }
        ".." -> {
            val pwd = State.PWD()
            if (pwd != "/") {
                val parent = pwd.take(pwd.lastIndexOf("/"))
                if (parent == "") {
                    State.navStack.add("/")
                } else {
                    State.navStack.add(parent)
                }
            }
        }
        else -> {
            val fullPath = if (expandedPath.startsWith("/")) {
                expandedPath
            } else if (State.PWD() == "/") {
                "/$expandedPath"
            } else {
                "${State.PWD()}/$expandedPath"
            }

            if (expandedPath.contains(".")) {
                echo("cd: ${expandedPath}: Not a directory")
            } else if (State.dir.containsKey(fullPath)) {
                State.navStack.add(fullPath)
            } else {
                echo("cd: ${fullPath}: No such file or directory")
            }
        }
    }
}