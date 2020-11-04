package com.hemendra.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    companion object{
        var baseurl = "https://jsonplaceholder.typicode.com/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var _retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build()
        var api = _retrofit.create(Api::class.java)

        var call :Call<Posts> = api.getPosts();
        call.enqueue(object:Callback<Posts>{
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                var titlearray = mutableListOf<String>()

                var responsearraylist = response!!.body()

                if (responsearraylist != null) {
                    for (postItem in responsearraylist)
                    {
                        titlearray.add(postItem.title)
                    }

                    var arradapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,titlearray)
                  var lv= findViewById<ListView>(R.id.lview)
                    lv.adapter = arradapter

                }
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }
}