package service

import command.echo

fun parseCommand(command: String): List<List<String>> {
    val regex = """\$\w+""".toRegex()
    val expandedCommand = regex.replace(command) { match -> State.env[match.value] ?: "" }

    val commandStrings = expandedCommand.split(";").map { it.trim() }

    return commandStrings.mapIndexed { index, commandString ->
        val trimmedCommandString = commandString.trim()
        if (trimmedCommandString.isBlank()) {
            if (index == commandStrings.size - 1) return@mapIndexed emptyList()
            echo("bash: Syntax error near unexpected token `;`")
            return emptyList()
        }

        val args = mutableListOf<String>()
        val currentArg = StringBuilder()
        var inQuotes = false
        var quoteChar = ' '

        for (char in trimmedCommandString) {
            if (char == '\'' || char == '"') {
                if (inQuotes) {
                    if (char == quoteChar) inQuotes = false
                    else currentArg.append(char)
                } else {
                    inQuotes = true
                    quoteChar = char
                }
            } else if (char.isWhitespace() && !inQuotes) {
                if (currentArg.isEmpty()) continue
                args.add(currentArg.toString())
                currentArg.clear()
            } else currentArg.append(char)
        }

        if (currentArg.isNotEmpty()) args.add(currentArg.toString())
        args.toList()
    }.filter { it.isNotEmpty() }
}