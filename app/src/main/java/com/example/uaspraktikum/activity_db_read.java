package com.example.uaspraktikum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_db_read extends AppCompatActivity implements AdapterMhsRecycleView.FirebaseDataListener {

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mhs> daftarMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mengeset layout
        setContentView(R.layout.activity_db_read);

        /**
         * inisialisasi RecycleView & komponennya
         */
        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
            database = FirebaseDatabase.getInstance().getReference();
        /**
         * Mengambil data dari Firebase Realtime DB
         */
        database.child("mahasiswa");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList

                 */
                daftarMahasiswa = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object mahasiswa
                     * Dan juga menyimpan primary key pada object mahasiswa
                     * untuk keperluan Edit dan Delete data
                     */
                    Mhs mhs = noteDataSnapshot.getValue(Mhs.class);
                    mhs.setKey(noteDataSnapshot.getKey());
                    /**
                     * Menambahkan object Mahasiswa yang sudah dimapping
                     * ke dalam ArrayList

                     */
                    daftarMahasiswa.add(mhs);
                }
                /**
                 * Inisialisasi adapter dan data Dosen dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new AdapterMhsRecycleView(daftarMahasiswa, activity_db_read.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + "" + databaseError.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, activity_db_read.class);
    }

    @Override
    public void onDeleteData(Mhs mahasiswa, int position) {
        if (database!=null){ database.child("mahasiswa").child(mahasiswa.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity_db_read.this, "Success delete", Toast.LENGTH_LONG).show();
            }
        });

        }
    }
}
