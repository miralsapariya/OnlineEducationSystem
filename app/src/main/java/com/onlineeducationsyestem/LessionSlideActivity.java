package com.onlineeducationsyestem;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LessionSlideActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession_slide);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);

        webView = findViewById(R.id.webView);
       // progressbar =findViewById(R.id.progressbar);



        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
       String myPdfUrl = "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf";
       // String myPdfUrl="https://brevity.symmetryplatform.com/brevity/uploads/Course/Document_Files/1594213922_6Cz.docx";
       // String myPdfUrl ="https://brevity.symmetryplatform.com/brevity/uploads/Course/Document_Files/1594214645_wJv.ppt";
        //String myPdfUrl ="https://file-examples.com/wp-content/uploads/2017/02/file_example_XLSX_5000.xlsx";

       // String myPdfUrl = "https://docs.google.com/spreadsheets/d/1zdFhFWbprCVFdPCypu_ToRkKCEAIY8TVn2balQh5Cao/edit#gid=0";
        String url = "https://docs.google.com/gview?embedded=true&url="+myPdfUrl;
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
           /* @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }*/

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               // progressbar.setVisibility(View.GONE);
            }
        });

    }



  /*  WebView urlWebView = (WebView)findViewById(R.id.containWebView);
urlWebView.setWebViewClient(new AppWebViewClients());
urlWebView.getSettings().setJavaScriptEnabled(true);
urlWebView.getSettings().setUseWideViewPort(true);
urlWebView.loadUrl("http://docs.google.com/gview?embedded=true&url="
        + "YOUR_DOC_URL_HERE");

    public class AppWebViewClients extends WebViewClient {



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
    }*/
}
