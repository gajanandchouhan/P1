package com.superlifesecretcode.app.ui.webview;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;

public class WebViewActivity extends BaseActivity implements LeadView {
    private WebView webView;
    private ProgressBar progressBar;
    LinearLayout layoutJoin;
    private String bannerId;
    private boolean showJoin;
    private LeadPresenter presenter;
    private TextView textViewJoin;
    private LanguageResponseData conversionData;

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initializeView() {
        String title = getIntent().getBundleExtra("bundle").getString("title");
        boolean isLink = getIntent().getBundleExtra("bundle").getBoolean("is_link");
        bannerId = getIntent().getBundleExtra("bundle").getString("id");
        showJoin = getIntent().getBundleExtra("bundle").getBoolean("join");
        setUpToolbar(title);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);
        layoutJoin = findViewById(R.id.layout_join);
        textViewJoin = findViewById(R.id.text_view_join);
        if (showJoin)
            layoutJoin.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        if (isLink) {
            webView.loadUrl(getIntent().getBundleExtra("bundle").getString("url"));
        } else {
            webView.loadData(getIntent().getBundleExtra("bundle").getString("content"), "text/html; charset=utf-8", "utf-8");
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        layoutJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> body = new HashMap<>();
                body.put("banner_id", bannerId);
                body.put("user_id", SuperLifeSecretPreferences.getInstance().getUserData().getUser_id());
                presenter.joinLead(body);
            }
        });
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        textViewJoin.setText(conversionData != null ? conversionData.getJoin() : "Join");
    }

    private void setUpToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(title);

    }

    @Override
    protected void initializePresenter() {
        presenter = new LeadPresenter(this);
        presenter.setView(this);

    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webView.onResume();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        webView = null;
        super.onDestroy();
    }

    @Override
    public void onLeadJoined(String already) {
        if (already != null && already.equalsIgnoreCase("1")) {
            CommonUtils.showSnakeBar(this, conversionData.getAlready_join());
        } else {
            CommonUtils.showSnakeBar(this, conversionData.getJoin_success());
        }
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            } else if (url.startsWith("zoomus:")) {
                if (appInstalledOrNot("us.zoom.videomeetings")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);

                    } catch (Exception e) {

                    }
                    return true;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=us.zoom.videomeetings"));
                        startActivity(intent);
                    } catch (Exception e) {

                    }

                    return true;
                }
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {

                }
                return true;
            }
        }

        private boolean appInstalledOrNot(String uri) {
            PackageManager pm = getPackageManager();
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
            }

            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
