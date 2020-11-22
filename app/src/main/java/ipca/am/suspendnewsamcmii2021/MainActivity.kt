package ipca.am.suspendnewsamcmii2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


//http://newsapi.org/v2/everything?q=bitcoin&from=2020-10-22&sortBy=publishedAt&apiKey=API_KEY

class MainActivity : AppCompatActivity() {

    var articles : List<Article> = arrayListOf()

    var adapter : ArticlesAdapter? = null

    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            viewModel = ViewModelProvider( this).get(ActivityViewModel::class.java)
            viewModel.articles.observe(this, Observer {
                articles = it
                adapter?.notifyDataSetChanged()

            })

        viewModel.setArticlesType("bitcoins")

        val listView = findViewById<ListView>(R.id.listsViews)
        adapter = ArticlesAdapter()
        listView.adapter = adapter


    }

    inner class ArticlesAdapter :   BaseAdapter() {
        override fun getCount(): Int {
           return articles.size
        }

        override fun getItem(p0: Int): Any {
            return articles[p0]

        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {

            val v = layoutInflater.inflate(R.layout.row_articles,p2,  false)
            val textViewTitle =  v.findViewById<TextView>(R.id.textViewTitle)
            val texViewDescription = v.findViewById<TextView>(R.id.textViewDescription)


            textViewTitle.text      = articles[p0].title
            texViewDescription.text = articles[p0].description

            return v

        }

    }
}