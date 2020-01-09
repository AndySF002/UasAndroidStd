package com.example.uaspraktikum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

public class SingleActivity extends AppCompatActivity {

    private Button btn_submit;
    private EditText et_Nim;
    private EditText et_Nama;
    private EditText et_prodi;
    //private TextView Showlocationtxt;
    //private Button getlocationBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreate);
        et_Nim = (EditText) findViewById(R.id.nim);
        et_Nama = (EditText) findViewById(R.id.nama_mhs);
        et_prodi = (EditText) findViewById(R.id.prodi);
        btn_submit = (Button) findViewById(R.id.bt_submit);
        //Showlocationtxt = (TextView) findViewById(R.id.getLocation);
        //getlocationBtn = (Button) findViewById(R.id.getLocation);

        et_Nim.setEnabled(false);
        et_Nama.setEnabled(false);
        et_prodi.setEnabled(false);
        //Showlocationtxt.setEnabled(false);
        btn_submit.setVisibility(View.GONE);
        //getlocationBtn.setVisibility(View.GONE);

        Mhs mhs = (Mhs) getIntent().getSerializableExtra("data");
        if (mhs!=null){
            et_Nim.setText(mhs.getNim());
            et_Nama.setText(mhs.getNama());
            et_prodi.setText(mhs.getProdi());
            //Showlocationtxt.setText(mhs.getLocation());

        }
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, SingleActivity.class);
    }
}
