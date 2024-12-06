package download

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

private val Int.asDoubleDigit: String
    get() = "$this".padStart(2, '0')

fun inputFile(year: Int, day: Int, location: File, suffix: String = ""): File =
    File(location, year.asDoubleDigit) + "Day${day.asDoubleDigit}$suffix.txt"

private operator fun File.plus(s: String): File = File(this, s)

private fun readFile(location: File, fileName: String): String {
    val tokenFile = location + fileName
    if (!tokenFile.exists()) {
        throw FileNotFoundException("You don't have a day input, but you don't have a $fileName either.")
    }
    return tokenFile.readText()
}

private fun createScraper(location: File) = AoCWebScraper(
    readFile(location, "sessionToken.txt"),
    readFile(location, "repository.txt"),
    readFile(location, "contactEmail.txt")
)

suspend fun ensureInputExists(year: Int, day: Int, location: File, suffix: String = ""): File {
    val yearFolder = File(location, year.asDoubleDigit)
    if (!yearFolder.exists()) yearFolder.mkdir()
    val inputFile = yearFolder + "Day${day.asDoubleDigit}$suffix.txt"
    if (inputFile.exists()) {
        return inputFile
    }

    val scraper = createScraper(location)
    val data = scraper.use {
        it.grabInput(year, day)
    }
    return withContext(Dispatchers.Default) {
        launch(Dispatchers.IO) {
            inputFile.writeText(data.dropLastWhile { it == '\n' })
        }
        inputFile
    }
}
