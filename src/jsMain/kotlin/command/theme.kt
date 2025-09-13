package command

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement

fun theme(theme: List<String>) {
    val defaults = listOf(
        "--terminal-bg" to "#121212",
        "--panel-bg" to "#181818",
        "--primary" to "limegreen",
        "--secondary" to "yellowgreen",
        "--tertiary" to "lime",
        "--font-size" to "1rem",
        "--font-family" to "'Courier New', Courier, monospace"
    )

    val style = (document.documentElement as? HTMLElement)?.style ?: return
    val computedStyle = window.getComputedStyle(document.documentElement!!)
    val get = { index: Int -> computedStyle.getPropertyValue(defaults[index].first).trim() }

    if (theme.isEmpty()) {
        echo(
            """
            Terminal Color: ${get(0)}
            Panel Color: ${get(1)}
            Text Color: ${get(2)}
            Prompt/Divider Color: ${get(3)}
            Matrix/Resize Color: ${get(4)}
            Font Size: ${get(5)}
            Font Family: ${get(6)}
            """.trimIndent()
        )
    }

    for (i in 0..5) {
        if (i >= theme.size) break
        if (theme[i] != ".") {
            style.setProperty(
                defaults[i].first,
                if (theme[i] == "-") defaults[i].second else theme[i]
            )
        }
    }

    if (theme.size > 6 && theme[6] != ".") {
        val fontFamilyValue = if (theme[6] == "-") defaults[6].second
            else if (theme[6].contains("\"")) theme[6]
            else "'${theme[6]}'"
        style.setProperty(defaults[6].first, fontFamilyValue)
    }
}