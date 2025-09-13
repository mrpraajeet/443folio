package command

import kotlinx.browser.window
import kotlinx.coroutines.await
import service.expandPath

suspend fun cat(file: String) {
    if (file.isBlank()) {
        echo("cat: Missing file operand")
        return
    }

    val expandedPath = expandPath(file)
    val fullPath = if (expandedPath.startsWith("/")) {
        expandedPath
    } else if (State.PWD() == "/") {
        "/$expandedPath"
    } else {
        "${State.PWD()}/$expandedPath"
    }

    if (!State.dir.containsKey(fullPath)) {
        echo("cat: ${fullPath}: No such file or directory")
    } else if (!fullPath.contains(".")) {
        echo("cat: ${fullPath}: Is a directory")
    } else {
        val fetchedFile = window.fetch("./assets/${file.split("/").last()}").await()
        val textContent = fetchedFile.text().await()
        echo(textContent)
    }
}