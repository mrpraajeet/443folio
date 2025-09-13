package service

fun expandPath(path: String): String {
    return if (path == "~") "/"
        else if (path.startsWith("~/")) path.replaceFirst("~/", "/")
        else if (path.startsWith("./")) path.replaceFirst("./", "")
        else path
}