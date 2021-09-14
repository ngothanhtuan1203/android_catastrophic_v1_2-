package com.tnmobile.catastrophic.utilily

import android.util.Log
import com.tnmobile.catastrophic.domain.model.Content
import com.tnmobile.catastrophic.domain.model.ReaderViewItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup

class SmartViewHelper {

    suspend fun parse(url: String): Flow<ReaderViewItem?> = flow {
        try {
            val document = Jsoup.connect(url).get()

            val authorName = document.select("div.post-meta__author-name").first()
                .text()
            val publishedDate = document.select("div.post-meta__publish-date").first()
                .text()
            val title = document.select("h1.post__title").first().text()
            val subTitle = document.select("p.post__lead").first().text()
            val category = document.select("span.post-cover__badge").first().text()
            val coverImageUrls = document.select("picture")
                .first().select("source").first().attr("srcset")
                .split(" ")
            val coverImageUrl = if (coverImageUrls.isNotEmpty()) coverImageUrls.first() else ""
            val contents = document.select("div.post-content").first()


            val contentList: MutableList<Content> = arrayListOf()
            contents.children().forEach { content ->
                val type = when (content.tagName()) {
                    "blockquote" -> ReadType.BLOCKQUOTE
                    "h2" -> ReadType.HEADING
                    "figure" -> ReadType.FIGURE
                    else -> ReadType.PARAGRAPH
                }

                val hyperLinks: MutableList<Pair<String, String>> = arrayListOf()
                if (type == ReadType.FIGURE) {
                    hyperLinks.add(
                        Pair(
                            content.select("img")
                                .first().attr("src"), ""
                        )
                    )
                } else {
                    content.children().forEach { c ->
                        Log.d(
                            "SmartViewHelper",
                            "Content: ${c.attributes()} - ${c.tagName()} - ${c.text()}"
                        )
                        if (c.tagName() == "a") {
                            hyperLinks.add(Pair(c.attr("href"), c.text()))
                        }
                    }
                }

                val articleContent = Content(
                    type, content.text(), hyperLinks
                )
                contentList.add(articleContent)
            }

            emit(ReaderViewItem(
                authorName,
                publishedDate,
                title,
                subTitle,
                category,
                coverImageUrl,
                url,
                contentList

            ))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null)
        }

    }

}