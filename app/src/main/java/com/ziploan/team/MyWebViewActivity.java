package com.ziploan.team;


import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.databinding.ActivityBrowserBinding;
import com.ziploan.team.utils.AppConstant;

public class MyWebViewActivity extends BaseActivity implements View.OnClickListener {
    private String urlTOLoad;
    private ActivityBrowserBinding bindedViews;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        bindedViews = (ActivityBrowserBinding) views;
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(AppConstant.Key.EXTRA_WEB_URL)){
            urlTOLoad = bundle.getString(AppConstant.Key.EXTRA_WEB_URL);
            loadInWebView(bindedViews.webView,urlTOLoad);
        }

        bindedViews.ivBack.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                finish();
                break;
        }
    }
    private void loadInWebView(WebView webView,String urlTOLoad) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyChromeClient());
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" +urlTOLoad);
    }

    public static void start(Context context, Bundle bundle){
        Intent intent = new Intent(context, MyWebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            bindedViews.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bindedViews.progressBar.setVisibility(View.GONE);
        }
    }

    private class MyChromeClient extends WebChromeClient {
    }
}
