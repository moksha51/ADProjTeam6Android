package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MapActivity extends AppCompatActivity {
    private String mUrl;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(MainActivity.EXTERNAL_URL);

        // Initialize webview and launch the url
        mWebView = findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);

    }
}