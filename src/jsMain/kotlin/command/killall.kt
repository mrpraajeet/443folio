package command

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement

fun killall() {
    State.abort = true
    val canvas = document.querySelector("canvas") as? HTMLCanvasElement
    if (canvas != null) {
        canvas.style.opacity = "0"
        window.setTimeout({ canvas.remove() }, 2000)
    }
}