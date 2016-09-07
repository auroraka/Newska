
package com.ihandy.a2014011310.ui.support;


import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.ui.support.BaseWebViewActivity;

public class WebViewLocalActivity extends BaseWebViewActivity {
    @Override
    protected String getData() {
        return  getIntent().getStringExtra(getString(R.string.id_html_content));
    }
    @Override
    protected void loadData() {
        //webView.loadDataWithBaseURL("about:blank", data, "text/html", "utf-8", null);
        webView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"+data, "text/html", "utf-8", null);
    }
}
