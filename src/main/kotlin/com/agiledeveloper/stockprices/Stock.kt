package com.agiledeveloper.stockprices


data class Stock (val ticker:String,val price:Int){
    companion object{
        fun fetchPrice(ticker: String) = Stock(ticker,(Math.random()*2000).toInt())
    }
}
