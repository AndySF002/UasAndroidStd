package com.example.uaspraktikum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterMhsRecycleView extends RecyclerView.Adapter<AdapterMhsRecycleView.ViewHolder> {

    private ArrayList<Mhs> daftarMahasiswa;
    private Context context;
    FirebaseDataListener listener;
    public AdapterMhsRecycleView(ArrayList<Mhs> mhswa, Context ctx) {
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarMahasiswa = mhswa;
        daftarMahasiswa = mhswa;
        context = ctx;
        listener = (activity_db_read) ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Inisiasi View
         * * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namamahasiswa);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        /**
         * Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mhs, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya\
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * Menampilkan data pada view
         */
        final String name = daftarMahasiswa.get(position).getNim();
        System.out.println("Daftar Mahasiswa one By one"+ position+daftarMahasiswa.size());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Read detail data

                 */
               context.startActivity(SingleActivity.getActIntent((Activity)context).putExtra
                       ("data", daftarMahasiswa.get(position)));
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 * Delete dan update data

                 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = (Button)dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button)dialog.findViewById(R.id.bt_delete_data);

                //aksi tombol edit ketika di klik
                editButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        context.startActivity(activity_dbcreate.getActIntent((Activity)
                                context).putExtra("data", daftarMahasiswa.get(position)));
                    }

                }
                );

                //aksi button delete ketika diklik
                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /**
                         * Delete Data
                         */
                        dialog.dismiss();
                        listener.onDeleteData(daftarMahasiswa.get(position), position);
                    }
                }
                );

                return true;
            }
        });
        holder.tvTitle.setText(name);
    }
    @Override
    public int getItemCount(){
        /**
         * mengembalikan jumlah item

         */
        return daftarMahasiswa.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Mhs mahasiswa, int position);
    }
}
