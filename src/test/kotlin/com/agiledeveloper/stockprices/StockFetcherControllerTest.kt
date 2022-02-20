package com.agiledeveloper.stockprices

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@Client("/stockFetcher")
interface StockPriceClient{
    @Get("/price/{tickers}")
    fun getPrices(tickers :String):List<Stock>
}

@MicronautTest
class StockFetcherControllerTest(private val embeddedServer: EmbeddedServer) {
    @Inject
    lateinit var stockPriceClient: StockPriceClient

    @Test
    fun testServerIsRunning() {
        assert(embeddedServer.isRunning())
    }

    @Test
    fun testIndex() {
        val client: HttpClient = embeddedServer.applicationContext.createBean(HttpClient::class.java, embeddedServer.url)
        assertEquals(HttpStatus.OK, client.toBlocking().exchange("/stockFetcher", String::class.java).status())
        client.close()
    }

    @Test
    fun getPrices(){
        val stocks = stockPriceClient.getPrices("GOOG,AMZN,ORCL")
        assertEquals(3,stocks.size)
        assertEquals("GOOG",stocks[0].ticker)
        assertTrue(stocks[0].price>0)
    }
}
