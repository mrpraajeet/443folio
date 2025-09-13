package command

import kotlinx.browser.window

data class Alias(
    val command: String,
    val execute: suspend () -> Unit
)

val aliases: Map<String, Alias> = mapOf(
    "default" to Alias(
        command = "theme - - - - - -",
        execute = { theme(listOf("-", "-", "-", "-", "-", "-", "-")) }
    ),
    "docker" to Alias(
        command = "open https://hub.docker.com/u/crinjal",
        execute = { window.open("https://hub.docker.com/u/crinjal", "_blank") }
    ),
    "email" to Alias(
        command = "open mailto:mr@praajeet.dev",
        execute = { window.open("mailto:mr@praajeet.dev", "_blank") }
    ),
    "github" to Alias(
        command = "open https://github.com/mrpraajeet",
        execute = { window.open("https://github.com/mrpraajeet", "_blank") }
    ),
    "help" to Alias(
        command = "cat /Help.txt",
        execute = { cat("/Help.txt") }
    ),
    "linkedin" to Alias(
        command = "open https://linkedin.com/in/praajeet",
        execute = { window.open("https://linkedin.com/in/praajeet", "_blank") }
    ),
    "ncat" to Alias(
      command = "open https://ncat.co.in/Result_NCAT_2023.aspx#&panel1-2",
        execute = { window.open("https://ncat.co.in/Result_NCAT_2023.aspx#&panel1-2", "_blank") }
    ),
    "optimus" to Alias(
        command = "open https://optimus.praajeet.dev",
        execute = { window.open("https://optimus.praajeet.dev", "_blank") }
    ),
    "resume" to Alias(
        command = "open ./assets/Resume.pdf",
        execute = { window.open("./assets/Resume.pdf", "_blank") }
    ),
    "whatsapp" to Alias(
        command = "open https://wa.me/918428349482",
        execute = { window.open("https://wa.me/918428349482", "_blank") }
    )
)