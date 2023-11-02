package com.example.smartcanedebug;

        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.io.IOException;

public class GuideActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);

        WebView webview = findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/guide.html");

    }







    //TextView select = findViewById(R.id.select);

    // getting data from intent.
    //  String name = getIntent().getStringExtra("key");

    // setting data to our text view.
    //  select.setText(name);

      /*  btn = (Button) findViewById(R.id.bt);
        txv = (TextView) findViewById(R.id.tx);

    }

            public void count(View view) {
                mCounter++;
                txv.setText(Integer.toString(mCounter));

            }
    public void count1(View view) {
        mCounter--;
        txv.setText(Integer.toString(mCounter));

    }


    }

//    @Override
//    public void onBackPressed() {
//        Intent intentMain = new Intent(this, MainActivity.class);
//        startActivity(intentMain);
//        finish();
//    }



*/

    public void goBackPressed(View view) {
        onBackPressed();
    }
}



