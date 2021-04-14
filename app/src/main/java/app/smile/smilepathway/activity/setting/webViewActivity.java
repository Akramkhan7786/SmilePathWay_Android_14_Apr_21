package app.smile.smilepathway.activity.setting;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.smile.smilepathway.R;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class webViewActivity extends AppCompatActivity {


    @BindView(R.id.tvHeaderTitle)
    TextView tvHeaderTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.webView)
    WebView htmlWebView;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    String url = "";
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("type")) {
            tvHeaderTitle.setText(getIntent().getStringExtra("type"));
            url = getIntent().getStringExtra("url");
        }
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        htmlWebView.getSettings().setJavaScriptEnabled(true); // enable javascript
        htmlWebView.getSettings().setLoadWithOverviewMode(true);
        htmlWebView.getSettings().setUseWideViewPort(true);
        htmlWebView.getSettings().setBuiltInZoomControls(false);

        htmlWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Utils.showInfoMsg(webViewActivity.this, description);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar2.setVisibility(View.VISIBLE);
                //setProgressBar();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar2.setVisibility(View.GONE);
                //progressEnd();
            }

        });
        htmlWebView.loadUrl(url);
    }


    @OnClick({R.id.ivBack, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
    }
}
