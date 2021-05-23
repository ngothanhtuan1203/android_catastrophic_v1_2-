package com.tnmobile.catastrophic.presentation.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.tnmobile.catastrophic.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*

/**
 * Detail activity, show the content of news.
 */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_fragment)
        val urls = intent.extras?.get("url").toString()
        cat_enlarge_image.load(urls) {
            crossfade(true)
            crossfade(1000)
            transformations(RoundedCornersTransformation())
            scale(Scale.FILL)
        }
    }
}