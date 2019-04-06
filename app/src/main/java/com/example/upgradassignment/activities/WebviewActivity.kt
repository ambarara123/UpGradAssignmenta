package com.example.upgradassignment.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.upgradassignment.networking.ApiService
import com.example.upgradassignment.utils.Utils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : AppCompatActivity() {

    companion object {
        val clientId = "14808"
        val apiKey = "jYuVa92)bUO9UWaAJaH43w(("
        val redirectUrl = "https://stackexchange.com/oauth/login_success"
        val TOKENURL = "TokenUrl"

    }

    val apiService by lazy {
        ApiService.create()
    }
    var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.upgradassignment.R.layout.activity_webview)




        webview!!.settings.javaScriptEnabled = true
        webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        val url =
            Utils.BASE_URL_LOGIN + "?client_id=" + clientId + "&scope=write_access" + "&redirect_uri=" + redirectUrl + ""

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
                if (url.contains("#access_token"))
                    return true

                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress_spinner.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {


                if (url!!.contains("#access_token")) {
                    Log.d("user", "user")


                    val token_str = url
                    val str = token_str.split("access_token=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                    val token = str[1].substring(0, str[1].length - 14)
                    finishAct(token)


                    /*//calling getuserId
                    disposable =
                            apiService.getUserId("stackoverflow", apiKey,token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { result ->  finishAct(result.items.get(0).userId!!);},
                                    { error -> Toast.makeText(this@WebviewActivity,error.localizedMessage+error.message,Toast.LENGTH_SHORT).show() }
                                )*/
                }

            }

        }
        webview.loadUrl(url)

    }

    private fun finishAct(UserId: String) {
        Log.d("user", UserId)

        /* Consts.UserId = UserId
         PreferencesHelper.setLoginCheck(true)
         PreferencesHelper.setUserID(UserId)*/
        this.getSharedPreferences("app", Context.MODE_PRIVATE)
            .edit().putBoolean("login", true)
            .putString("id", UserId)
            .apply()


        val i = Intent(this@WebviewActivity, UserInterestActivty::class.java)
        startActivity(i)
        this@WebviewActivity.finish()
        // overridePendingTransition(com.example.upgradassignment.R.anim.slide_activity_in_right, R.anim.slide_activity_out_right)
    }
/*
    fun clearCookies() {
        CookieSyncManager.createInstance(this@WebviewActivity)
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
    }*/

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
