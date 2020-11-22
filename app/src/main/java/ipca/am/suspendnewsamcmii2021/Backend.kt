package ipca.am.suspendnewsamcmii2021

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers.IO
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


//http://newsapi.org/v2/everything?q=bitcoin&from=2020-10-22&sortBy=publishedAt&apiKey=c7dc7d5938da41cfbfca1b6cf5434cfb

class Backend {

    companion object {

        const val BASE_API = "http://newsapi.org/v2/"

        fun fetchArticles(typesNews: String) = liveData(IO) {

            emit(getNews(typesNews))
        }


        suspend fun getNews(publishedAt: String) = suspendCoroutine<List<Article>> {

            try {

                val urlc: HttpURLConnection = URL(BASE_API
                        + "everything?q=bitcoin&from=2020-10-22&sortBy=$publishedAt&apiKey=c7dc7d5938da41cfbfca1b6cf5434cfb"

                ).openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connetion", "close")
                urlc.setConnectTimeout(1500)
                urlc.connect()

                val stream = urlc.inputStream
                val isReader = InputStreamReader(stream)
                val brin = BufferedReader(isReader)
                var str: String = ""

                var keepReading = true
                while (keepReading) {
                    var line = brin.readLine()
                    if (line == null) {
                        keepReading = false

                    } else {
                        str += line


                    }

                }
                brin.close()
                val result = JSONObject(str)
                if (result.getString("Status") == "ok") {
                    var articlesJSONArray = result.getJSONArray("articles")
                    val articles: MutableList<Article> = arrayListOf()

                    for (index in 0 until articlesJSONArray.length()) {

                        val jsonArticle: JSONObject = articlesJSONArray.get(index) as JSONObject
                        var article = Article.fromJSON(jsonArticle)
                        articles.add(article)
                    }
                    it.resume(articles)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }
}