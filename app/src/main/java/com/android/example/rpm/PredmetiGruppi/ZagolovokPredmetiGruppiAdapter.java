package com.android.example.rpm.PredmetiGruppi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokPredmetiGruppiAdapter extends RecyclerView.Adapter<ZagolovokPredmetiGruppiAdapter.ZagolovokPredmetiGruppiViewHolder>{

    private ArrayList<ZagolovokPredmetiGruppi> zagolovokPredmetiGruppis;
    private ZagolovokPredmetiGruppiAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokPredmetiGruppiAdapter(ArrayList<ZagolovokPredmetiGruppi>zagolovokPredmetiGruppis){
        this.zagolovokPredmetiGruppis=zagolovokPredmetiGruppis;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokPredmetiGruppiAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokPredmetiGruppiAdapter.ZagolovokPredmetiGruppiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_predmeti_gruppi,parent,false);
        return new ZagolovokPredmetiGruppiAdapter.ZagolovokPredmetiGruppiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokPredmetiGruppiAdapter.ZagolovokPredmetiGruppiViewHolder holder, int position) {
        ZagolovokPredmetiGruppi zagolovokPredmetiGruppi=zagolovokPredmetiGruppis.get(position);
        holder.Pole_Predmetov.setText(zagolovokPredmetiGruppi.getPolepredmetov());
    }

    @Override
    public int getItemCount() {return zagolovokPredmetiGruppis.size();}

    public class ZagolovokPredmetiGruppiViewHolder extends RecyclerView.ViewHolder {
        private TextView Pole_Predmetov;

        public ZagolovokPredmetiGruppiViewHolder(@NonNull View itemView){
            super(itemView);
            Pole_Predmetov=itemView.findViewById(R.id.PolePredmetov);
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