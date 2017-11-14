package com.example.vishnunarayn.continueonpc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connect = (Button) findViewById(R.id.connect);
        final EditText IPAddress = (EditText) findViewById(R.id.IPAddress);
        final TextView result = (TextView) findViewById(R.id.result);
        final EditText password = (EditText)findViewById(R.id.password);

        SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        IP = sp.getString("IPADDR", "DEFAULT");
        pass = sp.getString("PASS", "DEFAULT");
        result.setText(IP);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IPTemp = IPAddress.getText().toString();
                String passTemp = password.getText().toString();
                if(!passTemp.isEmpty())
                    pass = passTemp;
                if(!IPTemp.isEmpty())
                    IP = IPTemp;
                result.setText(IP);
                finish();
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra(Intent.EXTRA_TEXT);

        if(url != null ) {
            new sendToPhone().execute(url, IP, pass);
            finish();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        SharedPreferences sp = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IPADDR",  IP);
        editor.putString("PASS", pass);
        editor.commit();

    }
}

    class sendToPhone extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... args) {
            String url = args[0];
            String IP = args[1];
            String pass = args[2];
            try {
                Socket s = new Socket(IP, 27015);
                DataOutputStream PC = new DataOutputStream(s.getOutputStream());
                PC.writeUTF(pass+" "+url);
                PC.flush();
                PC.close();
                s.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            return "Excuted";
        }

    }