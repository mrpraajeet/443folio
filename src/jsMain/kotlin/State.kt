import kotlinx.browser.document
import kotlinx.browser.window

object State {
    fun terminalWidth(): Int {
        if (window.innerWidth <= 1024) return window.innerWidth
        val style = window.getComputedStyle(document.body!!)
        val get = { property: String, default: Int ->
            style.getPropertyValue(property)
            .trim()
            .removeSuffix("px")
            .toIntOrNull() ?: default
        }
        return window.innerWidth - get("--panel-width", 360) - get("--resizer-width", 4)
    }

    const val HOME = "/"
    val HOSTNAME = window.location.hostname
    const val UNAME = "Linux"
    var USER = "guest"

    val navStack: MutableList<String> = mutableListOf("/")
    val PWD = { navStack.last() }
    val OLDPWD = { navStack.getOrNull(navStack.size - 2) ?: "/" }
    val PS1: String
        get() { return if (terminalWidth() <= 430) "${USER}$" else "${USER}@${HOSTNAME}:${PWD()}$" }

    val cmdHistory: MutableList<String> = mutableListOf()
    var cmdPointer = 0
    var typedCmd = ""
    val cmdList = listOf(
        "alias", "cat", "cd", "clear", "curl", "date", "demo", "echo", "env", "exit",
        "grep", "help", "history", "hostname", "killall", "ls", "matrix", "open", "pwd", "reboot",
        "su", "theme", "uname", "whoami"
    )

    var abort: Boolean = false
    var isTabbed: Boolean = false


    val dir: Map<String, Array<String>> = mapOf(
        "/" to arrayOf("Praajeet", "About.txt", "Help.txt"),
        "/Praajeet" to arrayOf("Resume.pdf"),
        "/About.txt" to arrayOf(),
        "/Help.txt" to arrayOf()
    )

    val env: Map<String, String?>
        get() = mapOf(
            "\$HOSTNAME" to HOSTNAME,
            "\$HOME" to HOME,
            "\$OLDPWD" to OLDPWD(),
            "\$PS1" to PS1,
            "\$PWD" to PWD(),
            "\$USER" to USER,
        )
}