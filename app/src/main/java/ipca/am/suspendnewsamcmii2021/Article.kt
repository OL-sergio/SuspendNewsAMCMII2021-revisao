package ipca.am.suspendnewsamcmii2021

import org.json.JSONObject

class Article  {

    var title       :   String? = null
    var description :   String? = null
    var url         :   String? = null


    constructor(title: String?, description : String?, url: String? ){

            this.title          = title
            this.description    = description
            this.url            = url
    }

   constructor()

    companion object{

        fun fromJSON ( jsonObject: JSONObject) : Article {
            val article = Article()

            article.title = jsonObject.getString("title")
            article.description = jsonObject.getString("description")
            article.url = jsonObject.getString("url")


            return article
        }
   }
}

