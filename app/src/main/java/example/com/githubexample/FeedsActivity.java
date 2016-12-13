package example.com.githubexample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Aqeel.mirza on 12/13/2016.
 */

public class FeedsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeds_layout);

        String username = getIntent().getStringExtra("username");

        WebView webView = (WebView) findViewById(R.id.feeds_view);
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("https://github.com/" + username);


    }
}
