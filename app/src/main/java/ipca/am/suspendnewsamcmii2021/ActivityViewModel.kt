package ipca.am.suspendnewsamcmii2021

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class ActivityViewModel : ViewModel() {

    private var _articlesType : MutableLiveData<String> = MutableLiveData()

    val articles =  _articlesType.switchMap {

        Backend.fetchArticles(it)

    }
    fun setArticlesType(articlesType: String){
        val update = articlesType
        if(_articlesType.value == update ) return
            _articlesType.value = update

    }

}