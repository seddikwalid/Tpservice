package com.example.wido.tpservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.onServ);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startServ();
            }

        });


    }

    public void startServ() {


        Toast.makeText(getApplicationContext(), "flash on", Toast.LENGTH_SHORT).show();
        Intent flash =new Intent(getApplicationContext(),Flash.class);
        startService(flash);

    }
}
