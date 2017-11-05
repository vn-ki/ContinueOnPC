package com.example.vishnunarayn.continueonpc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.Socket;



public class MainActivity extends AppCompatActivity {

    private String IP;
    private static final String TAG = "Shit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
*/
        Button connect = (Button) findViewById(R.id.connect);
        final EditText IPAddress = (EditText) findViewById(R.id.IPAddress);
        final TextView result = (TextView) findViewById(R.id.result);

        SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        IP = sp.getString("IPADDR", "DEFAULT");
        result.setText(IP);
        Log.i(TAG, "HI");

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IP = IPAddress.getText().toString();
                result.setText(IP);

            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra(Intent.EXTRA_TEXT);

        if(url != null ) {
            new sendToPhone().execute(url, IP);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IPADDR",  IP);
        editor.commit();

    }
}

    class sendToPhone extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... args) {
            String url = args[0];
            String IP = args[1];
            try {
                Socket s = new Socket(IP, 27015);
                DataOutputStream PC = new DataOutputStream(s.getOutputStream());
                PC.writeUTF(url);
                PC.flush();
                PC.close();
                s.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            return "Excuted";
        }

    }