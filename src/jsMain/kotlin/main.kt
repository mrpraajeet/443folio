import command.killall
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLDivElement
import kotlin.js.Date
import service.*

val scope = MainScope()

fun main() {
    inputListener()
    keydownListener()
    resizeListener()
    splitViewListener()
    window.addEventListener("popstate", { killall() })

    val isToday = { d: Int, m: Int -> Date().getDate() == d && Date().getMonth() + 1 == m }
    val hearts = isToday(14, 2) || isToday(1, 5) || isToday(1, 8) || isToday(26, 11) || isToday(9, 12)

    scope.launch {
        val curtain = document.createElement("div") as HTMLDivElement
        curtain.id = "curtain"
        document.body?.appendChild(curtain)

        newPrompt()
        simulateCommand("matrix 5" + if (hearts) " <3" else "")
        delay(1000)
        window.setTimeout({ curtain.remove() }, 1000)
        simulateCommand("cat About.txt")
    }
}