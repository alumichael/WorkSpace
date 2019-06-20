package com.mike4christ.tisvdigital.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mike4christ.tisvdigital.R;

public class WebFragment extends Fragment {
    public static String TAG = "webview";
    public ProgressBar bar;
    
    public WebView mywebView;
    public View view1;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 1) {
                webViewGoBack();
            }
            return false;
        }
    });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        mywebView =  view.findViewById(R.id.webview);
        bar =  view.findViewById(R.id.progressBar);
        view1 = view.findViewById(R.id.view);
        mywebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {

                bar.setVisibility(View.VISIBLE);
                bar.setProgress(progress);
                if (progress == 100) {
                    bar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, progress);
            }
        });
        loadweb();
        return view;
    }

    public void loadweb() {
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebView.getSettings().setRenderPriority(RenderPriority.HIGH);
        mywebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mywebView.getSettings().setAppCacheEnabled(true);
        mywebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setSupportZoom(true);
        mywebView.setWebViewClient(new myWebViewClient());
        mywebView.loadUrl("https://www.tisv.digital/");
        mywebView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode != 4 || keyEvent.getAction() != 1 || !mywebView.canGoBack()) {
                    return false;
                }
                handler.sendEmptyMessage(1);
                return true;
            }
        });
        bar.setProgress(0);
    }


    public class myWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            bar.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            return super.shouldOverrideUrlLoading(view, request);
        }



        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view1.setVisibility(View.INVISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }


    private void webViewGoBack() {
        mywebView.goBack();
    }
}
