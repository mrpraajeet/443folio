package command

import kotlinx.coroutines.delay
import service.newPrompt
import service.simulateCommand

suspend fun demo(interval: Int = 2) {
    newPrompt()
    State.abort = false

    val commands = listOf(
        "echo Hello World!",
        "su demo",
        "date",
        "ls",
        "cd Praajeet; ls",
        "env",
        "cd ..",
        "sudo rm -rf Praajeet",
        "xyz",
        "theme",
        "theme black . #00a0a0 rgb(180,180,0) . . serif",
        "default",
        "grep or About.txt -n -i",
        "grep \"or my\" About.txt",
        "su guest",
        "clear",
        "history",
        "cat Help.txt"
    )

    val delayMs = if (interval > 0) interval * 1000L else 2000L
    for (i in 0 until commands.size) {
        if (State.abort) {
            State.abort = false
            return
        }
        simulateCommand(commands[i])
        if (i < commands.size - 1) delay(delayMs)
    }
}