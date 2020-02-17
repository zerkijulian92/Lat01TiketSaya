package com.takatutustudio.lat01tiketsaya;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.myViewHolder>{

    Context context;
    ArrayList<MyTicket> myTicket;

    public TicketAdapter(Context c, ArrayList<MyTicket> p) {
        context  = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView xNamaWisata, xLokasi, xJumlahTiket;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            xNamaWisata  = itemView.findViewById(R.id.xnama_wisata);
            xLokasi      = itemView.findViewById(R.id.xlokasi);
            xJumlahTiket = itemView.findViewById(R.id.xjumlah_tiket);
        }
    }
}
