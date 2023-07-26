package com.android.example.rpm.Kabinet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokKabinetAdapter extends RecyclerView.Adapter<ZagolovokKabinetAdapter.ZagolovokKabinetViewHolder>{

    private ArrayList<ZagolovokKabinet> zagolovokKabinets;
    private ZagolovokKabinetAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokKabinetAdapter(ArrayList<ZagolovokKabinet>zagolovokKabinets){
        this.zagolovokKabinets=zagolovokKabinets;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokKabinetAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokKabinetAdapter.ZagolovokKabinetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_kabinet,parent,false);
        return new ZagolovokKabinetAdapter.ZagolovokKabinetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokKabinetAdapter.ZagolovokKabinetViewHolder holder, int position) {
        ZagolovokKabinet zagolovokKabinet=zagolovokKabinets.get(position);
        holder.Nomer.setText(zagolovokKabinet.getNomer());
        holder.Vid.setText(zagolovokKabinet.getVid());
    }

    @Override
    public int getItemCount() {return zagolovokKabinets.size();}

    public class ZagolovokKabinetViewHolder extends RecyclerView.ViewHolder {
        private TextView Nomer;
        private TextView Vid;

        public ZagolovokKabinetViewHolder(@NonNull View itemView){
            super(itemView);
            Nomer=itemView.findViewById(R.id.Nomer);
            Vid=itemView.findViewById(R.id.Vid);
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

