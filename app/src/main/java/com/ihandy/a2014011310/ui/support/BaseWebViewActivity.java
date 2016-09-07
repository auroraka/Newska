
package com.ihandy.a2014011310.ui.support;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;

public abstract class BaseWebViewActivity extends SwipeBackActivity {
    protected WebView webView;
    protected ProgressBar progressBar;
    protected boolean isLoading = true;
    protected String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initData();
    }
    protected abstract String getData();
    protected abstract void loadData();
    private void initData() {
        data = getData();

        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        /*
         cache web page
         */

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

       /*  if(Settings.isNightMode) {
            webView.setBackgroundColor(ContextCompat.getColor(this, R.color.night_primary));
        }*/
        if(HttpUtil.isWIFI == false) {
            webView.getSettings().setBlockNetworkImage(Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false));
        }else {
            // fix issue #13
            webView.getSettings().setBlockNetworkImage(false);
        }

        loadData();
        setProgressBarIndeterminateVisibility(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (isLoading) {
                    progressBar.incrementProgressBy(newProgress - progressBar.getProgress());
                    if (newProgress > 25) {
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
