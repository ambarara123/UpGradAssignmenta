package com.example.upgradassignment.activities

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.upgradassignment.R
import kotlinx.android.synthetic.main.activity_webview.*

class BrowserActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)


        if (intent.hasExtra("url") && intent != null) {

            val url = intent.getStringExtra("url")

            Log.d("url", url)

            webview!!.settings.javaScriptEnabled = true
            webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY




            webview!!.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    setProgress(newProgress * 100)

                    if (newProgress >= 100)
                        progress_spinner.visibility = View.GONE
                    else
                        progress_spinner.visibility = View.VISIBLE
                }


            }



            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progress_spinner.visibility = View.VISIBLE
                }
            }

            webview.loadUrl(url)
        }
    }
}
