package service

import State
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.asList

fun newPrompt(prompt: String = State.PS1, command: String? = "") {
    val inputs = document.querySelectorAll("input").asList().filterIsInstance<HTMLInputElement>()
    if (inputs.isNotEmpty()) inputs.last().readOnly = true

    val line = document.createElement("div") as HTMLDivElement
    line.classList.add("line")

    val span = document.createElement("span") as HTMLSpanElement
    span.textContent = prompt

    val input = document.createElement("input") as HTMLInputElement
    input.type = "text"
    input.spellcheck = false

    line.appendChild(span)
    line.appendChild(input)
    document.body?.appendChild(line)

    input.focus()
    if (document.activeElement == input) {
        (document.activeElement as? HTMLInputElement)?.value = command ?: State.typedCmd
        State.cmdPointer = State.cmdHistory.size
    }
}