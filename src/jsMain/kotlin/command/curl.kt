package command

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.dom.url.URL

suspend fun curl(args: List<String>) {
    /* val getFlag = { flag: String -> args.indexOf(flag).let {
        index -> if (index == -1 || index == args.size - 1) null else args[index + 1]
    } }
    val data = getFlag("-d")
    val (user, pass) = getFlag("-u")?.split(":") ?: listOf(null, null)
    val method = getFlag("-X") ?: "GET" */

    val url = args.find { isUrl(it) } ?: return
    val proxyUrl = "https://api.allorigins.win/raw?url=$url"

    try {
        val response = window.fetch(proxyUrl).await()
        val text = response.text().await()
        echo(text)
    } catch (e: dynamic) {
        console.error(e)
    }
}

private fun isUrl(url: String): Boolean {
    return try {
        URL(url)
        true
    } catch (e: dynamic) {
        console.error(e)
        false
    }
}