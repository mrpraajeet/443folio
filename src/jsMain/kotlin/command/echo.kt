package command

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

fun echo(vararg texts: String, escapeHtml: Boolean = true) {
    texts.forEach { text ->
        val escapedText = if (escapeHtml) escapeHtml(text) else text
        val replacedText = escapedText.replace("""\n""", "<br>")
        val pre = document.createElement("pre")
        pre.innerHTML = replacedText
        document.body?.appendChild(pre)
    }
}

private fun escapeHtml(unsafe: String): String {
    val tempDiv = document.createElement("div") as HTMLDivElement
    tempDiv.textContent = unsafe
    return tempDiv.innerHTML
}