package com.example.justmemes

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var CurrentImageUrl: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme() {
        // Instantiate the RequestQueue.
        progressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val memeurl = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, memeurl,null,
            { response ->
                CurrentImageUrl = response.getString("url")
                Glide.with(this).load(CurrentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility =View.GONE
                        return false

                    }


                }).into(memeImageView)

            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }


    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this Cool Meme from Reddit by JustMemes $CurrentImageUrl")
        val chooser = Intent(Intent.createChooser(intent,"Share This Meme Using"))
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}

