package command

import kotlinx.browser.window
import kotlinx.coroutines.await

suspend fun grep(args: MutableList<String>) {
    val count = args.remove("-c")
    val insensitive = args.remove("-i")
    val numbered = args.remove("-n")
    val invert = args.remove("-v")
    val whole = args.remove("-w")

    if (args.isEmpty()) echo("grep: Missing pattern operand")
    if (args.size < 2) {
        echo("grep: Missing file operand")
        return
    }

    val pattern = (if (whole) "\\b${args[0]}\\b" else args[0]).removeSurrounding("\"").removeSurrounding("'")
    val files = args.drop(1)
    val regex = if (insensitive) pattern.toRegex(RegexOption.IGNORE_CASE) else pattern.toRegex()

    files.forEach { file ->
        if (!State.dir.containsKey("/$file")) {
            echo("grep: ${file}: No such file or directory")
            return@forEach
        } else if (!file.contains(".")) {
            echo("grep: ${file}: Is a directory")
            return@forEach
        }

        val lines = window.fetch("./assets/$file").await().text().await().lines()
        val filePrefix = if (files.size > 1) "$file:" else ""
        var occurrences = 0

        for (i in lines.indices) {
            val line = lines[i]
            if (regex.containsMatchIn(line) == invert) continue
            occurrences++
            if (count) continue

            val linePrefix = if (numbered) "${i + 1}:" else ""
            val highlighted = line.replace(regex, "<span><b>$0</b></span>")
            echo("$filePrefix$linePrefix$highlighted", escapeHtml = false)
        }

        if (count) echo("$filePrefix$occurrences")
    }
}