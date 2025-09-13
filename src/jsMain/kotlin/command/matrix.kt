package command

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.floor
import kotlin.random.Random

fun matrix(args: List<String>) {
    val duration = args.getOrNull(0)?.toIntOrNull() ?: 600
    val fps = args.getOrNull(1)?.toIntOrNull() ?: 20
    val heart = args.lastOrNull() == "<3"

    val canvas = document.createElement("canvas") as HTMLCanvasElement
    canvas.width = window.innerWidth
    canvas.height = window.innerHeight
    document.body?.appendChild(canvas)
    val context = canvas.getContext("2d") as CanvasRenderingContext2D

    val hearts = listOf(
        "❤️", "🩷", "🩵", "🖤", "💜", "💙", "💚", "💛", "🧡", "🤍", "🤎", "💔",
        "❤️‍🔥", "❤️‍🩹", "💖", "💗", "💓", "💞", "💕", "💘", "💝", "💟", "❣️", "💌"
    )

    val charSet:() -> String = if (heart) { { hearts.random() } }
        else { { Random.nextInt(128).toChar().toString() } }

    val style = window.getComputedStyle(document.documentElement!!)
    val color = style.getPropertyValue("--tertiary")
    val fontSize = style.getPropertyValue("font-size")
        .removeSuffix("px").toDoubleOrNull() ?: 16.0
    val fontFamily = style.getPropertyValue("font-family")

    val tail = IntArray(floor(canvas.width / fontSize).toInt()) {
        Random.nextInt(floor(canvas.height / fontSize).toInt().coerceAtLeast(1))
    }

    val draw = {
        context.fillStyle = "rgba(0, 0, 0, 0.05)"
        context.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        context.fillStyle = color
        context.font = "${fontSize}px $fontFamily"

        tail.forEachIndexed { x, y ->
            context.fillText(charSet(), x * fontSize, y * fontSize)
            if (y * fontSize > canvas.height && Random.nextDouble() > 0.95) tail[x] = 0
            tail[x]++
        }
    }

    val interval = window.setInterval(draw, 1000 / fps)
    window.setTimeout({
        canvas.style.opacity = "0"
        window.setTimeout({ canvas.remove() }, 2000)
    }, duration * 1000)
    window.setTimeout({ window.clearInterval(interval) }, duration * 1000 + 1000)
}