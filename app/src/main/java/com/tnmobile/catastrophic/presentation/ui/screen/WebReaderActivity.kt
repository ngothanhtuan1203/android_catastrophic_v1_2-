package com.tnmobile.catastrophic.presentation.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.tnmobile.catastrophic.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_web_reader.*

/**
 * Detail activity, show the content of news.
 */

@AndroidEntryPoint
class WebReaderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_reader)

       val urls = intent.extras?.get("url").toString();
        setupWebView()


        repo_web_view.loadUrl(urls)
    }

    private fun setupWebView() {
        repo_web_view.setInitialScale(1)
        val webSettings = repo_web_view.settings
        webSettings.setAppCacheEnabled(false)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true

    }
}