package com.shankhadeepghoshal.containersdemo.post.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.Executors

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

private val kLogger = KotlinLogging.logger {}

@Service
class RestService(
    @Value("\${post.rest.client.connection-timeout}")
    private val timeoutDuration: Duration,
    @Value("\${post.rest.user-base-url}")
    private val baseUrlUser: String,
    @Value("\${post.rest.client.thread-pool-size}")
    private val threadPoolSize: Int
) {
    private val executorService = Executors.newFixedThreadPool(threadPoolSize)
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .connectTimeout(timeoutDuration)
        .executor(executorService)
        .version(HttpClient.Version.HTTP_1_1)
        .build()

    fun signalUserServiceIncreasePostCount(authorId: Int): Boolean {
        return notifyUserServicePostCountChange(authorId, PostModificationState.INCREASE)
    }

    fun signalUserServiceDecreasePostCount(authorId: Int): Boolean {
        return notifyUserServicePostCountChange(authorId, PostModificationState.DECREASE)
    }

    private fun notifyUserServicePostCountChange(
        authorId: Int,
        pms: PostModificationState
    ): Boolean {

        val restReq = when (pms) {
            PostModificationState.INCREASE -> getHttpRequest("$baseUrlUser/$authorId/increase")
            PostModificationState.DECREASE -> getHttpRequest("$baseUrlUser/$authorId/decrease")
        }

        kLogger.debug("Request received to send request for url {} for {}", restReq.uri(), pms.name)
        kLogger.info("Duration $timeoutDuration")
        val response = httpClient.send(restReq, BodyHandlers.ofString(StandardCharsets.UTF_8))
        return response.statusCode() == HttpStatus.OK.value()
    }

    private fun getHttpRequest(url: String): HttpRequest {
        return HttpRequest.newBuilder()
            .uri(URI.create(url))
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build()
    }

    private enum class PostModificationState {
        INCREASE,
        DECREASE
    }
}
