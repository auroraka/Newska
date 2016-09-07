

package com.ihandy.a2014011310.ui.about;

import com.ihandy.a2014011310.ui.support.WebViewUrlActivity;

public class AppIntroActivity extends WebViewUrlActivity {
    @Override
    protected void loadData() {
        webView.loadUrl("file:///android_asset/NewskaIntroduction.html");
    }
}
