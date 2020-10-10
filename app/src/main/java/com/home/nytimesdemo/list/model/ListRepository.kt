package com.home.nytimesdemo.list.model

import org.jsoup.Jsoup

class ListRepository {

    fun getListResponse(): ListResponse {
        val response = ListResponse()
        val requestUrl = "https://my-gamer.com/uga1.html"
        val document = Jsoup.connect(requestUrl).get()
        val element = document.getElementById("tabs-1")
            .select("table").select("tbody").select("tr").select("td")
            .select("p").select("a")
        val nameList: MutableList<String> = mutableListOf()
        val urlList: MutableList<String> = mutableListOf()
        element.forEach { item ->
            val name = item.select("a").text()
            if (!name.isNullOrEmpty()) nameList.add(name)
            var urlZoo = item.select("img").attr("src")
            if (!urlZoo.isNullOrEmpty()) {
                urlZoo = urlZoo.replace("http", "https")
                urlList.add(urlZoo)
            }
        }
        val size = nameList.size
        for (i in 0 until size) {
            val name = nameList[i]
            val url = urlList[i]
            response.list.add(ListResponseImage(name, url))
        }
        return response
    }
}