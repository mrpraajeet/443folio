package service

import command.echo
import command.killall
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent
import scope

fun keydownListener() {
    document.body?.addEventListener("keydown", { event ->
        val e = event as KeyboardEvent
        if (e.ctrlKey && e.shiftKey && e.key.lowercase() == "c") {
            e.preventDefault()
            val selectedText = window.asDynamic().getSelection()?.toString() ?: ""
            scope.launch { window.navigator.clipboard.writeText(selectedText) }
            return@addEventListener
        }

        val active = document.activeElement
        if (active == null || active.tagName != "INPUT") return@addEventListener
        val activeInput = active as HTMLInputElement

        when (e.key) {
            "Enter" -> {
                e.preventDefault()
                scope.launch { executeCommand(activeInput.value) }
            }
            "ArrowDown" -> {
                e.preventDefault()
                if (State.cmdPointer < State.cmdHistory.size) {
                    activeInput.value = if (++State.cmdPointer < State.cmdHistory.size)
                        State.cmdHistory[State.cmdPointer] else State.typedCmd
                }
            }
            "ArrowUp" -> {
                e.preventDefault()
                if (State.cmdHistory.isNotEmpty()) {
                    if (State.cmdPointer == State.cmdHistory.size) State.typedCmd = activeInput.value
                    if (State.cmdPointer > 0) --State.cmdPointer
                    activeInput.value = State.cmdHistory[State.cmdPointer]
                }
            }
        }

        if (e.ctrlKey && e.shiftKey && e.key.lowercase() == "v") {
            e.preventDefault()
            scope.launch {
                val text = window.navigator.clipboard.readText().await()
                activeInput.value += text
                if (State.cmdPointer == State.cmdHistory.size) State.typedCmd = activeInput.value
            }
        } else if (e.ctrlKey) {
            e.preventDefault()
            when (e.key.lowercase()) {
                "a" -> activeInput.setSelectionRange(0, 0)
                "c" -> killall()
                "d" -> if (activeInput.value.isEmpty()) scope.launch { simulateCommand("exit") }
                "e" -> activeInput.setSelectionRange(activeInput.value.length, activeInput.value.length)
                "l" -> scope.launch { simulateCommand("clear") }
                "k" -> activeInput.value = activeInput.value.substring(0, activeInput.selectionStart ?: 0)
                "u" -> {
                    activeInput.value = activeInput.value.substring(activeInput.selectionStart ?: 0)
                    activeInput.setSelectionRange(0, 0)
                }
            }
        } else if (e.key == "Tab") {
            e.preventDefault()
            if (activeInput.value.isBlank()) return@addEventListener
            val suggestions = State.cmdList.filter { it.startsWith(activeInput.value) }

            if (suggestions.size > 1 && !State.isTabbed) {
                State.isTabbed = true
                return@addEventListener
            } else if (suggestions.size == 1) {
                activeInput.value = suggestions[0] + " "
            } else if (suggestions.size > 1) {
                echo(suggestions.joinToString("  "))
                newPrompt(command = activeInput.value)
            }
            State.isTabbed = false
        } else {
            State.isTabbed = false
        }
    })
}