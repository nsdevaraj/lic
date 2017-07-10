package fm.nsdevaraj.lic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class FullscreenActivity extends Activity {

    // set your custom url here
    String url = "https://ebiz.licindia.in/D2CPM/#MLogin";
    //"http://nsdevaraj.github.io/fm";

    // if you want to show progress bar on splash screen
    Boolean showProgressOnSplashScreen = true;

    WebView mWebView;
    ProgressBar prgs;
    RelativeLayout splash, main_layout;

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
                Window.PROGRESS_VISIBILITY_ON);

        mWebView = (WebView) findViewById(R.id.wv);
        prgs = (ProgressBar) findViewById(R.id.progressBar);
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);

        // splash screen View
        if (!showProgressOnSplashScreen)
            ((ProgressBar) findViewById(R.id.progressBarSplash)).setVisibility(View.GONE);
        splash = (RelativeLayout) findViewById(R.id.splash);

        mWebView.loadUrl(url);
        // control javaScript and add html5 features
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setPluginsEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDatabasePath(
                this.getFilesDir().getPath() + this.getPackageName()
                        + "/databases/");

        // this force use chromeWebClient
        mWebView.getSettings().setSupportMultipleWindows(true);


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (prgs.getVisibility() == View.GONE) {
                    prgs.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (prgs.getVisibility() == View.VISIBLE)
                    prgs.setVisibility(View.GONE);

                // check if splash is still there, get it away!
                if (splash.getVisibility() == View.VISIBLE)
                    splash.setVisibility(View.GONE);

                CookieSyncManager.getInstance().sync();
            }

        });

    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
