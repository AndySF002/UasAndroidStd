package com.example.uaspraktikum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class activity_dbcreate extends AppCompatActivity {

    private DatabaseReference database;

   // private static final int REQUEST_LOCATION = 1;

    private Button btsumbit;
    private EditText etNim;
    private EditText etNama;
    private EditText etprodi;
    //private Button getlocationBtn;
    //private TextView showLocationTxt;
    //private String latitude,longitude;
    //private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreate);

        /*ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

         */

        //showLocationTxt = findViewById(R.id.show_location);
        //getlocationBtn = findViewById(R.id.getLocation);

        etNim = (EditText) findViewById(R.id.nim);
        etNama = (EditText) findViewById(R.id.nama_mhs);
        etprodi = (EditText) findViewById(R.id.prodi);
        btsumbit = (Button) findViewById(R.id.bt_submit);

        //mengambil referensi fields EditText dan Button
        database = FirebaseDatabase.getInstance().getReference();

        //Final Update
        final Mhs mahasiswa = (Mhs) getIntent().getSerializableExtra("data");
        if (mahasiswa != null) {
            //ini untuk Update
            etNim.setText(mahasiswa.getNim());
            etNama.setText(mahasiswa.getNama());
            etprodi.setText(mahasiswa.getProdi());
            //showLocationTxt.setText(mahasiswa.getLocation());

            btsumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mahasiswa.setNim(etNim.getText().toString());
                    mahasiswa.setNama(etNama.getText().toString());
                    mahasiswa.setProdi(etprodi.getText().toString());
                    //mahasiswa.setLocation(showLocationTxt.getText().toString());
                    updateMhs(mahasiswa);
                }
            });
        } else {
            //ini untuk Input
            //kode pemanggilan Button
            btsumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEmpty(etNim.getText().toString()) && !isEmpty(etNama.getText().toString())) {
                        submitMhs(new Mhs(etNim.getText().toString(),
                                etNama.getText().toString(), etprodi.getText().toString()));
                    } else {
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Mahasiswa tidak boleh kosong",
                                Snackbar.LENGTH_LONG).show();
                    }
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etNama.getWindowToken(), 0);
                }
            });
        }
      /*  getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //Mengecek apakah gps aktif atau belum
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //membaca fungsi untuk menghidupkan GPS
                    OnGPS();
                }
                else
                {
                    //jika GPS sudah on lalu
                    getLocation();
                }

            }
        });*/

    }


    private boolean isEmpty(String s){
        //mengecek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateMhs(Mhs mahasiswa) {
        //Update Mahasiswa
        database.child("mahasiswa").child(mahasiswa.getKey()).setValue(mahasiswa)
                .addOnSuccessListener(this, new OnSuccessListener<Void>(){
           @Override
            public void onSuccess(Void aVoid){
               Snackbar data_berhasil_di_update = Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil di Update",
                       Snackbar.LENGTH_LONG);
               data_berhasil_di_update.setAction("OKE", new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       finish();
                   }
               });
               data_berhasil_di_update.show();
           }
        });
    }

    private void submitMhs(Mhs mahasiswa){
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime
         Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("mahasiswa").push().setValue(mahasiswa).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNim.setText("");
                etNama.setText("");
                etprodi.setText("");
               // showLocationTxt.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

   /* private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(activity_dbcreate.this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity_dbcreate.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
*/
    public static Intent getActIntent(Activity activity){
        //kode untuk pengambilan Intent
        return new Intent(activity, activity_dbcreate.class);
    }
}
