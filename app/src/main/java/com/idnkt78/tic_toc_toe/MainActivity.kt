package com.idnkt78.tic_toc_toe

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // Set status bar background color to white
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)

        // Set status bar icons to dark
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        setupWebView()
        setupBackPressHandler()

        // Load the website
        webView.loadUrl("https://tic-toc-toe-delta-six.vercel.app/")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.webView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.webViewClient = WebViewClient() // Prevent external browser

        webView.settings.apply {
            javaScriptEnabled = true // Required for most modern web apps
            domStorageEnabled = true // Enable local storage (many PWAs need this)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
    }

    private fun setupBackPressHandler() {
        // Modern way to override back button
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}