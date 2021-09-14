package com.tnmobile.catastrophic.presentation.ui.screen

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import coil.api.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.ReaderViewItem
import com.tnmobile.catastrophic.utilily.ReadType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_smartview.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch

/**
 * Detail activity, show the content of news.
 */
@ExperimentalPagingApi
@AndroidEntryPoint
class SmartReaderActivity : AppCompatActivity() {
    private lateinit var viewModel: NativeReaderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smartview)

        val urls = intent.extras?.get("url").toString()

        viewModel = ViewModelProvider(this).get(NativeReaderViewModel::class.java)
        viewModel.data.observe(this@SmartReaderActivity, renderView)
        viewModel.loadSmartViewData(urls)
    }

    private val renderView = Observer<ReaderViewItem> {
        lifecycleScope.launch {
            if (it != null) {
                updateSmartView(it)
            }

        }
    }
    private val hyperLinkClickListener = object : HyperLinkClickListener {
        override fun onHyperLinkClicked(url: String) {

            val intent = Intent(this@SmartReaderActivity, WebReaderActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
    }

    private fun updateSmartView(article: ReaderViewItem) {


        val tf = Typeface.createFromAsset(applicationContext.assets, "font/helvetica-light.ttf")
        val tfb = Typeface.createFromAsset(applicationContext.assets, "font/Helvetica.ttf")

        txt_author.typeface = tf
        txt_publish_date.typeface = tf
        txt_title.typeface = tfb
        txt_subTitle.typeface = tf

        txt_author.text = article.author
        txt_publish_date.text = article.publishDate
        txt_title.text = article.title
        txt_subTitle.text = article.subTitle

        cover_img.load(article.coverImgUrl) {
            crossfade(true)
            crossfade(1000)
            transformations(RoundedCornersTransformation())
            scale(Scale.FILL)
        }

        article.contents.forEach { c ->

            when (c.type) {
                ReadType.PARAGRAPH -> {
                    val tv: TextView = LayoutInflater.from(applicationContext)
                        .inflate(R.layout.item_smartview_paragraph, null) as TextView
                    val tf = Typeface.createFromAsset(
                        applicationContext?.assets,
                        "font/helvetica-light.ttf"
                    )
                    tv.typeface = tf
                    tv.text = c.rawContent
                    c.hyperLinks.forEach { h ->
                        setClickableHighLightedText(tv, h, hyperLinkClickListener)
                    }
                    container.addView(tv)
                }

                ReadType.BLOCKQUOTE -> {
                    val tv: TextView = LayoutInflater.from(applicationContext)
                        .inflate(R.layout.item_smartview_blockquote, null) as TextView

                    tv.text = c.rawContent
                    c.hyperLinks.forEach { h ->
                        setClickableHighLightedText(tv, h, hyperLinkClickListener)
                    }

                    container.addView(tv)
                }

                ReadType.HEADING -> {
                    val tv: TextView = LayoutInflater.from(applicationContext)
                        .inflate(R.layout.item_smartview_heading, null) as TextView
                    val tf = Typeface.createFromAsset(
                        applicationContext?.assets,
                        "font/helvetica-bold.ttf"
                    )
                    tv.typeface = tf
                    tv.text = c.rawContent
                    c.hyperLinks.forEach { h ->
                        setClickableHighLightedText(tv, h, hyperLinkClickListener)
                    }
                    container.addView(tv)
                }

                ReadType.FIGURE -> {
                    val img: ImageView = LayoutInflater.from(applicationContext)
                        .inflate(R.layout.item_smartview_figure, null) as ImageView

                    img.load(c.hyperLinks.first().first) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(RoundedCornersTransformation())
                        scale(Scale.FILL)
                    }
                    container.addView(img)
                }
            }

        }
    }

    interface HyperLinkClickListener {
        fun onHyperLinkClicked(url: String)
    }

    private fun setClickableHighLightedText(
        tv: TextView,
        hyperLink: Pair<String, String>,
        onClickListener: HyperLinkClickListener?
    ) {
        val tvt = tv.text.toString()
        var ofe = tvt.indexOf(hyperLink.second, 0)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#03DAC5")
                ds.underlineThickness = 4f
                ds.underlineColor = Color.parseColor("#03DAC5")
                ds.isUnderlineText = true
            }

            override fun onClick(widget: View) {
                onClickListener?.onHyperLinkClicked(hyperLink.first)
            }
        }
        val wordToSpan = SpannableString(tv.text)
        var ofs = 0
        while (ofs < tvt.length && ofe != -1) {
            ofe = tvt.indexOf(hyperLink.second, ofs)
            if (ofe == -1) break else {
                wordToSpan.setSpan(
                    clickableSpan,
                    ofe,
                    ofe + hyperLink.second.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
                tv.movementMethod = LinkMovementMethod.getInstance()
            }
            ofs = ofe + 1
        }
    }

}