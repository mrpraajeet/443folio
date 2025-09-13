package service

import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.InputEvent
import org.w3c.dom.events.InputEventInit
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.KeyboardEventInit

fun simulateCommand(command: String) {
    val input = document.activeElement as? HTMLInputElement ?: return
    input.value = command
    input.dispatchEvent(InputEvent("input", InputEventInit(bubbles = true)))
    document.body?.dispatchEvent(KeyboardEvent("keydown", KeyboardEventInit(key = "Enter", bubbles = true)))
}