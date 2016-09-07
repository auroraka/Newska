
package com.ihandy.a2014011310.ui.support;



import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.ui.support.BaseWebViewActivity;

public class WebViewUrlActivity extends BaseWebViewActivity {
    @Override
    protected String getData() {
        return getIntent().getStringExtra(getString(R.string.id_url));
    }
    @Override
    protected void loadData() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(data);
            }
        });
    }
}
