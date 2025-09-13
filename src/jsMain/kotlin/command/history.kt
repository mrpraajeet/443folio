package command

fun history(tail: String? = null) {
    val history = State.cmdHistory
    val digits = history.size.toString().length
    val echoFrom = history.size - 1 - (tail?.toIntOrNull() ?: (history.size + 1))

    history.forEachIndexed { i, s ->
        if (i <= echoFrom) return@forEachIndexed
        echo("${(i + 1).toString().padStart(digits, ' ')} $s")
    }
}