package ar.com.imaginar.ruletatermas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private FrameLayout root;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(0, 142, 160));
        setContentView(root);
        try {
            createWebView();
        } catch (Throwable t) {
            showError("Fallo al crear WebView", t);
        }
        immersive();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void createWebView() {
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(0, 142, 160));
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(false);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= 17) s.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onConsoleMessage(ConsoleMessage m) { return true; }
        });

        root.addView(webView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        String html = "<!doctype html><html><head><meta name='viewport' content='width=device-width,initial-scale=1'>"
                + "<style>html,body{margin:0;width:100%;height:100%;background:#008ea0;color:#fff;font-family:Arial}"
                + ".c{height:100%;display:flex;flex-direction:column;align-items:center;justify-content:center;text-align:center;padding:30px;box-sizing:border-box}"
                + "h1{font-size:42px;margin:0 0 20px}p{font-size:25px;line-height:1.35}.ok{color:#ffe34e;font-weight:bold}</style></head>"
                + "<body><div class='c'><h1>WEBVIEW OK</h1><p>La capa WebView se abrió correctamente.</p>"
                + "<p id='js'>Probando JavaScript…</p><p>Android " + Build.VERSION.RELEASE + " · API " + Build.VERSION.SDK_INT + "</p></div>"
                + "<script>setTimeout(function(){document.getElementById('js').innerHTML='<span class=ok>JAVASCRIPT OK</span>';},1000);</script>"
                + "</body></html>";

        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
    }

    private void showError(String title, Throwable t) {
        TextView v = new TextView(this);
        v.setTextColor(Color.WHITE);
        v.setBackgroundColor(Color.rgb(150, 30, 30));
        v.setTextSize(20f);
        v.setGravity(Gravity.CENTER);
        v.setPadding(40,40,40,40);
        v.setText(title + "\n\n" + t.getClass().getName() + "\n" + String.valueOf(t.getMessage()));
        root.removeAllViews();
        root.addView(v, new FrameLayout.LayoutParams(-1,-1));
    }

    @SuppressWarnings("deprecation")
    private void immersive() {
        try {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        } catch (Throwable ignored) {}
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) immersive();
    }

    @Override protected void onDestroy() {
        if (webView != null) {
            root.removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
