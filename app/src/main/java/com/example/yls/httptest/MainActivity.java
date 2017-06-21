package com.example.yls.httptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String urlstr = "http://a1.gdcp.cn/index.shtml";
    private Button btn;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btn = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.webText);
        // CTRL+SHIFT+空格
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlstr);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            InputStream is = conn.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is, "gb2312"));
                            StringBuffer buffer = new StringBuffer();

                            String line = br.readLine();
                            while(line != null){
                                buffer.append(line);
                                line = br.readLine();
                            }

                            final String webContent = buffer.toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt.setText(webContent);
                                }
                            });
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });


    }
}
