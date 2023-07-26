package com.android.example.rpm.Gruppa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokGruppuAdapter extends RecyclerView.Adapter<ZagolovokGruppuAdapter.ZagolovokGruppuViewHolder>{

    private ArrayList<ZagolovokGruppu> zagolovokGruppus;
    private ZagolovokGruppuAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokGruppuAdapter(ArrayList<ZagolovokGruppu>zagolovokGruppus){
        this.zagolovokGruppus=zagolovokGruppus;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokGruppuAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokGruppuAdapter.ZagolovokGruppuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_gruppu,parent,false);
        return new ZagolovokGruppuAdapter.ZagolovokGruppuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokGruppuAdapter.ZagolovokGruppuViewHolder holder, int position) {
        ZagolovokGruppu zagolovokGruppu=zagolovokGruppus.get(position);
        holder.NazvanieGruppu.setText(zagolovokGruppu.getNazvaniegruppu());
        holder.Kolichestvovgruppe.setText(zagolovokGruppu.getKolichestvovgruppe());
    }

    @Override
    public int getItemCount() {return zagolovokGruppus.size();}

    public class ZagolovokGruppuViewHolder extends RecyclerView.ViewHolder {
        private TextView NazvanieGruppu;
        private TextView Kolichestvovgruppe;

        public ZagolovokGruppuViewHolder(@NonNull View itemView){
            super(itemView);
            NazvanieGruppu=itemView.findViewById(R.id.NazvanieGruppu);
            Kolichestvovgruppe=itemView.findViewById(R.id.KolichestvoVGruppe);
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
