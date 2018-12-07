package com.example.httpreq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textViewUrl;
    EditText editTextUrl;
    Button buttonGet;
    EditText editTextResponse;
    TextView textViewTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewUrl = (TextView)findViewById(R.id.reqURLview);
        editTextUrl = (EditText)findViewById(R.id.data);
        buttonGet = (Button)findViewById(R.id.get);
        editTextResponse = (EditText)findViewById(R.id.response);
        editTextUrl.setText("http://10.201.42.73:3000/cells/元気");
    }

    public void onButtonGet(View view) {
        Toast.makeText(this,"click test",Toast.LENGTH_SHORT).show();
        textViewUrl.setText(editTextUrl.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(editTextUrl.getText().toString());
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    final String str = InputStreamToString(con.getInputStream());
                    Log.d("HTTP", str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextResponse.setText(String.valueOf(str));
                        }
                    });
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextResponse.setText("接続できてへんっすわ");
                        }
                    });
                    System.out.println(ex);
                }
            }
        }).start();
    }

    // InputStream -> String
    static String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}