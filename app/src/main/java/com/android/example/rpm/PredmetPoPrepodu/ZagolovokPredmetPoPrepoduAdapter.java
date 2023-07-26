package com.android.example.rpm.PredmetPoPrepodu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokPredmetPoPrepoduAdapter extends RecyclerView.Adapter<ZagolovokPredmetPoPrepoduAdapter.ZagolovokPredmetPoPrepoduViewHolder>{

    private ArrayList<ZagolovokPredmetPoPrepodu> zagolovokPredmetPoPrepodus;
    private ZagolovokPredmetPoPrepoduAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokPredmetPoPrepoduAdapter(ArrayList<ZagolovokPredmetPoPrepodu>zagolovokPredmetPoPrepodus){
        this.zagolovokPredmetPoPrepodus=zagolovokPredmetPoPrepodus;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokPredmetPoPrepoduAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokPredmetPoPrepoduAdapter.ZagolovokPredmetPoPrepoduViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_predmet_po_prepodu,parent,false);
        return new ZagolovokPredmetPoPrepoduAdapter.ZagolovokPredmetPoPrepoduViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokPredmetPoPrepoduAdapter.ZagolovokPredmetPoPrepoduViewHolder holder, int position) {
        ZagolovokPredmetPoPrepodu zagolovokPredmetPoPrepodu=zagolovokPredmetPoPrepodus.get(position);
        holder.Predmeti_Prepoda.setText(zagolovokPredmetPoPrepodu.getPredmeti_prepoda());
    }

    @Override
    public int getItemCount() {return zagolovokPredmetPoPrepodus.size();}

    public class ZagolovokPredmetPoPrepoduViewHolder extends RecyclerView.ViewHolder {
        private TextView Predmeti_Prepoda;

        public ZagolovokPredmetPoPrepoduViewHolder(@NonNull View itemView){
            super(itemView);
            Predmeti_Prepoda=itemView.findViewById(R.id.PredmetiPrepoda);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onNoteClickListener!=null){
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(onNoteClickListener!=null){
                        onNoteClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}