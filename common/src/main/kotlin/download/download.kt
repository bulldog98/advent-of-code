package download

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

private val Int.asDoubleDigit: String
    get() = "$this".padStart(2, '0')

fun inputFile(year: Int, day: Int, location: File, suffix: String = ""): File =
    File(location, year.asDoubleDigit) + "Day${day.asDoubleDigit}$suffix.txt"

private operator fun File.plus(s: String): File = File(this, s)

suspend fun ensureInputExists(year: Int, day: Int, location: File, suffix: String = ""): List<String> {
    val yearFolder = File(location, year.asDoubleDigit)
    if (!yearFolder.exists()) yearFolder.mkdir()
    val inputFile = yearFolder + "Day${day.asDoubleDigit}$suffix.txt"
    if (inputFile.exists()) {
        return inputFile.readLines()
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
            inputFile.writeText(data.dropLastWhile { it == '\n' })
        }
        async {
            data.lines().dropLastWhile { it == "\n" || it == "" }
        }.await()
    }
}
