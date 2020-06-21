package com.ziploan.team.collection.application_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ziploan.team.BuildConfig;
import com.ziploan.team.R;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;

import java.util.HashMap;
import java.util.Map;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;


public class ViewerActivity extends AppCompatActivity implements DownloadFile.Listener {

    WebView webview;
    ProgressBar progressbar;
    LinearLayout pdfView;
    RemotePDFViewPager remotePDFViewPager;

    PDFPagerAdapter adapter;
    private PDFViewPager pdfViewPager;
    Map<String, String> extraHeaders = new HashMap<>();


    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, ViewerActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.view_doc));

        webview = findViewById(R.id.webview);
        pdfViewPager = findViewById(R.id.pdfViewPager);
        progressbar = findViewById(R.id.progressbar);
        pdfView = findViewById(R.id.pdfView);

        try {
            openDocument();
        }catch (Exception e){
            progressbar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private void openDocument() {
        webview.setVisibility(View.VISIBLE);

        WebSettings settings = webview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);


        extraHeaders.put(AppConstant.Key.ACCESS_ID, ZiploanSPUtils.getInstance(this).getAccessId());
        extraHeaders.put(AppConstant.Key.ACCESS_TOKEN, ZiploanSPUtils.getInstance(this).getAccessToken());


        if (getIntent().getStringExtra("name").equalsIgnoreCase("esign")){

            String url1 = getIntent().getStringExtra("url");
            String urls = BuildConfig.BASE_URL + "media/" +url1;

            webview.loadUrl(urls, extraHeaders);
            webview.setWebChromeClient(new WebChromeClient());
            webview.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {

                        String url1 = getIntent().getStringExtra("url");
                        if (!TextUtils.isEmpty(url1)) {
                            String urls = BuildConfig.BASE_URL + "media/" +url1;
                            downloadFile(urls);
                            pdfView.setVisibility(View.VISIBLE);
                            pdfViewPager.setVisibility(View.VISIBLE);
                            webview.setVisibility(View.GONE);
                        } else {
                        pdfView.setVisibility(View.GONE);
                        pdfViewPager.setVisibility(View.GONE);
                        webview.setVisibility(View.VISIBLE);
                    }
                }
            });

        }else{

            webview.loadUrl(getIntent().getStringExtra("url"), extraHeaders);
            webview.setWebChromeClient(new WebChromeClient());
            webview.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    if ("application/pdf".equals(mimetype)) {
                        pdfView.setVisibility(View.VISIBLE);
                        pdfViewPager.setVisibility(View.VISIBLE);
                        webview.setVisibility(View.GONE);
                        String mLoanId = getIntent().getStringExtra("id");
                        if (!TextUtils.isEmpty(mLoanId)) {
                            String urls = BuildConfig.BASE_URL + "download_letters/?repayment_manager_id=" + ZiploanSPUtils.getInstance(ViewerActivity.this).getAccessId() + "&view_type=0&loan_application_number="
                                    + mLoanId + "&is_customer=false";
                            downloadFile(urls);
                        } else {
                            downloadFile(getIntent().getStringExtra("url"));
                        }
                    } else {
                        pdfView.setVisibility(View.GONE);
                        pdfViewPager.setVisibility(View.GONE);
                        webview.setVisibility(View.VISIBLE);
                    }
                }
            });

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressbar.getVisibility() == View.VISIBLE)
                    progressbar.setVisibility(View.GONE);

            }
        }, 2 * 1000);

    }

    private void downloadFile(String url) {
        remotePDFViewPager = new RemotePDFViewPager(this, url, this, extraHeaders);
        remotePDFViewPager.setId(R.id.pdfViewPager);

    }



    public static void open(Context context) {
        Intent i = new Intent(context, ViewerActivity.class);
        context.startActivity(i);
    }


    public void updateLayout() {
        pdfView.removeAllViewsInLayout();
        progressbar.setVisibility(View.GONE);
        pdfView.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        progressbar.setVisibility(View.GONE);

        updateLayout();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        pdfViewPager.setVisibility(View.GONE);

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //this.finish();
              //  return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
