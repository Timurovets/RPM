package com.android.example.rpm.GlavnaiOblast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokGlavnaiGruppiAdapter extends RecyclerView.Adapter<ZagolovokGlavnaiGruppiAdapter.ZagolovokGlavnaiGruppiViewHolder>{

    private ArrayList<ZagolovokGlavnaiGruppi> zagolovokGlavnaiGruppis;
    private ZagolovokGlavnaiGruppiAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokGlavnaiGruppiAdapter(ArrayList<ZagolovokGlavnaiGruppi>zagolovokGlavnaiGruppis){
        this.zagolovokGlavnaiGruppis=zagolovokGlavnaiGruppis;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokGlavnaiGruppiAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokGlavnaiGruppiAdapter.ZagolovokGlavnaiGruppiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_glavnai_gruppi,parent,false);
        return new ZagolovokGlavnaiGruppiAdapter.ZagolovokGlavnaiGruppiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokGlavnaiGruppiAdapter.ZagolovokGlavnaiGruppiViewHolder holder, int position) {
        ZagolovokGlavnaiGruppi zagolovokGlavnaiGruppi=zagolovokGlavnaiGruppis.get(position);
        holder.Pole_Predmetov_Raspisanie.setText(zagolovokGlavnaiGruppi.getPolepredmetovraspisanie());
    }

    @Override
    public int getItemCount() {return zagolovokGlavnaiGruppis.size();}

    public class ZagolovokGlavnaiGruppiViewHolder extends RecyclerView.ViewHolder {
        private TextView Pole_Predmetov_Raspisanie;

        public ZagolovokGlavnaiGruppiViewHolder(@NonNull View itemView){
            super(itemView);
            Pole_Predmetov_Raspisanie=itemView.findViewById(R.id.PolePredmetovRaspisanie);
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