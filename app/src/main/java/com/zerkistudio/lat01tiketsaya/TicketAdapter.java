package com.zerkistudio.lat01tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
        return new myViewHolder((LayoutInflater
                .from(context).inflate(R.layout.item_my_ticket,
                parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.xNamaWisata.setText(myTicket.get(position).getNama_wisata());
        holder.xLokasi.setText(myTicket.get(position).getLokasi());
        holder.xJumlahTiket.setText(myTicket.get(position).getJumlah_tiket() + " Tickets");

        final String getNamaWisata = myTicket.get(position).getNama_wisata();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyticketdetails = new Intent(context, MyTicketDetailAct.class);
                gotomyticketdetails.putExtra("nama_wisata", getNamaWisata);
                context.startActivity(gotomyticketdetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTicket.size();
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
