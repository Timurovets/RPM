package com.android.example.rpm.Raspisanie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokRaspisanieGruppAdapter extends RecyclerView.Adapter<ZagolovokRaspisanieGruppAdapter.ZagolovokRaspisanieGruppViewHolder>{

    private ArrayList<ZagolovokRaspisanieGrupp> zagolovokRaspisanieGrupps;
    private ZagolovokRaspisanieGruppAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokRaspisanieGruppAdapter(ArrayList<ZagolovokRaspisanieGrupp>zagolovokRaspisanieGrupps){
        this.zagolovokRaspisanieGrupps=zagolovokRaspisanieGrupps;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokRaspisanieGruppAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokRaspisanieGruppAdapter.ZagolovokRaspisanieGruppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_raspisanie_grupp,parent,false);
        return new ZagolovokRaspisanieGruppAdapter.ZagolovokRaspisanieGruppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokRaspisanieGruppAdapter.ZagolovokRaspisanieGruppViewHolder holder, int position) {
        ZagolovokRaspisanieGrupp zagolovokRaspisanieGrupp=zagolovokRaspisanieGrupps.get(position);
        holder.Pole_Predmetov_Raspisanie.setText(zagolovokRaspisanieGrupp.getPolepredmetovraspisanie());
    }

    @Override
    public int getItemCount() {return zagolovokRaspisanieGrupps.size();}

    public class ZagolovokRaspisanieGruppViewHolder extends RecyclerView.ViewHolder {
        private TextView Pole_Predmetov_Raspisanie;

        public ZagolovokRaspisanieGruppViewHolder(@NonNull View itemView){
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