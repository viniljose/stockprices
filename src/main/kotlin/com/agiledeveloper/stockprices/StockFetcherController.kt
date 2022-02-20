package com.agiledeveloper.stockprices

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/stockFetcher")
class StockFetcherController {

    @Get(uri="/", produces=["text/plain"])
    fun index(): String {
        return "Example Response"
    }

    @Get("/price/{tickers}")
    fun getPrices(tickers :String):List<Stock> =
        tickers.split(",")
            .map( Stock::fetchPrice )
}
