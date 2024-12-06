package download

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

// https://github.com/CognitiveGear/AdventOfCode-Kotlin/blob/main/common/src/main/kotlin/Utils.kt
class AoCWebScraper(
    private val sessionToken: String,
    private val repository: String,
    private val email: String,
) : Closeable {

    private val client = HttpClient(OkHttp) {
        install(ContentEncoding) {
            deflate()
            gzip()
        }
    }

    @Throws(ResponseException::class)
    suspend fun grabInput(year: Int, day: Int) : String {
        val data : String
        val response = client.get("https://adventofcode.com/$year/day/$day/input") {
            headers {
                append(
                    "User-Agent",
                    "$repository by $email"
                )
                append(
                    "cookie",
                    "session=$sessionToken"
                )
            }
        }
        when (response.status) {
            HttpStatusCode.Accepted, HttpStatusCode.OK -> data = response.body()
            else -> throw ResponseException(response, "AoC:: " + response.body())
        }
        return data
    }
    override fun close() {
        client.close()
    }
}