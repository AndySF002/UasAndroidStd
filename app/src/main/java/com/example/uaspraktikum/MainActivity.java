package com.example.uaspraktikum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCreateDB;
    private Button btViewDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCreateDB=(Button)findViewById(R.id.bt_createdata);
        btViewDB=(Button)findViewById(R.id.bt_viewdata);

        btCreateDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(activity_dbcreate.getActIntent(MainActivity.this));
            }
        });

        btViewDB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //kelas untuk View data
                startActivity(activity_db_read.getActIntent(MainActivity.this));
            }
        });
    }
}
