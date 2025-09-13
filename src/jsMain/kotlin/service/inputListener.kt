package service

import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.InputEvent

fun inputListener() {
    document.body?.addEventListener("input", { e ->
        val target = (e as? InputEvent)?.target
        if (target == document.activeElement
            && target is HTMLInputElement
            && target.readOnly
            && State.cmdPointer == State.cmdHistory.size) {
            State.typedCmd = target.value
        }
    })
}