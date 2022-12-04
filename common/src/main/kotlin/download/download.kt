package download

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

private val Int.asDoubleDigit: String
    get() = "$this".padStart(2, '0')

private operator fun File.plus(s: String): File = File(this, s)

suspend fun ensureInputExists(year: Int, day: Int, location: File, suffix: String = ""): List<String> {
    val yearFolder = File(location, year.asDoubleDigit)
    if (!yearFolder.exists()) yearFolder.mkdir()
    val fileName = yearFolder + "Day${day.asDoubleDigit}$suffix.txt"
    if (fileName.exists()) {
        return fileName.readLines()
    }
    val tokenFile = location + "sessionToken.txt"
    if (!tokenFile.exists()) {
        throw FileNotFoundException("You don't have a day input, but you don't have a sessionToken.txt either.")
    }
    val token = tokenFile.readText()

    val scraper = AoCWebScraper(token)
    val data = scraper.use {
        it.grabInput(year, day)
    }
    return withContext(Dispatchers.Default) {
        launch(Dispatchers.IO) {
            fileName.writeText(data.dropLastWhile { it == '\n' })
        }
        async {
            data.lines().dropLastWhile { it == "\n" || it == "" }
        }.await()
    }
}
