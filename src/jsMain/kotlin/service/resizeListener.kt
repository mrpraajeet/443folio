package service

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.NodeList

fun resizeListener() {
    var timer: Int? = null

    fun updatePrompts() {
        timer?.let { window.clearTimeout(it) }
        timer = window.setTimeout({
            val prompts: NodeList = document.querySelectorAll(".line > span")
            for (i in 0 until prompts.length) {
                prompts.item(i)?.textContent = State.PS1
            }
        }, 50)
    }

    window.addEventListener("resize", { updatePrompts() })
    window.addEventListener("split-resize", { updatePrompts() })
}