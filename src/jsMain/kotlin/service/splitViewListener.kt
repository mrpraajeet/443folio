package service

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CustomEvent
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.MouseEvent

fun splitViewListener() {
    val resizer = document.getElementById("resizer") as? HTMLDivElement ?: return
    var isResizing = false

    resizer.addEventListener("mousedown", { e ->
        isResizing = true
        (e as MouseEvent).preventDefault()
        document.body?.classList?.add("resizing")
    })

    window.addEventListener("mousemove", { event ->
        if (!isResizing) return@addEventListener
        val e = event as MouseEvent
        val width = (window.innerWidth - e.clientX).coerceIn(360, window.innerWidth - 360)
        document.body?.style?.setProperty("--panel-width", "${width}px")
        window.dispatchEvent(CustomEvent("split-resize"))
    })

    window.addEventListener("mouseup", {
        isResizing = false
        document.body?.classList?.remove("resizing")
    })
}