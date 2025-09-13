package service

import State
import command.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.js.Date

suspend fun executeCommand(command: String) {
    if (command.isBlank()) {
        newPrompt()
        State.typedCmd = ""
        State.cmdPointer = State.cmdHistory.size
        return
    }
    if (command != State.cmdHistory.lastOrNull()) State.cmdHistory.add(command)
    State.cmdPointer = State.cmdHistory.size - 1

    val unavailable = setOf(
        "awk", "chmod", "chown", "cp", "cron", "find", "git", "htop", "ifconfig", "kill",
        "ln", "locate", "mkdir", "mv", "nano", "ping", "pkill", "ps", "rm", "rmdir",
        "sed", "source", "sudo", "time", "top", "touch", "vi", "vim"
    )

    for (commandToExecute in parseCommand(command)) {
        val name = commandToExecute[0]
        val args = commandToExecute.drop(1)

        if (unavailable.contains(name)) {
            echo("${commandToExecute.joinToString(" ")}: Permission denied")
            continue
        } else if (aliases.contains(name)) {
            aliases[name]?.execute()
            continue
        }

        when (name) {
            "alias" -> aliases.forEach { (key, value) -> echo("$key='${value.command}'") }

            "cat" -> cat(args.getOrNull(0) ?: "")

            "cd" -> cd(args.getOrNull(0) ?: "")

            "clear" -> document.body?.innerHTML = "<div id=\"resizer\"></div>\n<div id=\"panel\"></div>"

            "curl" -> curl(args)

            "date" -> echo(Date().toString())

            "demo" -> demo(args.getOrNull(0)?.toIntOrNull() ?: 2)

            "echo" -> echo(args.joinToString(" "))

            "env" -> State.env.forEach { (key, value) -> echo("${key.substring(1)}=$value") }

            "exit", "poweroff", "shutdown" -> window.close()

            "grep" -> grep(args.toMutableList())

            "history" -> history(args.getOrNull(0))

            "hostname" -> echo(State.HOSTNAME)

            "killall" -> killall()

            "ls" -> echo(State.dir[State.PWD()]?.joinToString("\n") ?: "")

            "matrix" -> matrix(args)

            "open" -> window.open(args.getOrNull(0) ?: "", "_blank")

            "pwd" -> echo(State.navStack.last())

            "reboot" -> window.location.reload()

            "su" -> if (args.isEmpty()) echo("su: Operation not permitted")
                else State.USER = args[0]

            "theme" -> theme(args)

            "uname" -> echo(State.UNAME)

            "whoami" -> echo(State.USER)

            else -> echo("$name: Command not found")
        }
    }

    newPrompt(command = null)
    State.isTabbed = false
    State.typedCmd = ""
}