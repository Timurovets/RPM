package com.android.example.rpm.GlavnaiOblast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokGlavnaiKabinetAdapter extends RecyclerView.Adapter<ZagolovokGlavnaiKabinetAdapter.ZagolovokGlavnaiKabinetViewHolder>{

    private ArrayList<ZagolovokGlavnaiKabinet> zagolovokGlavnaiKabinets;
    private ZagolovokGlavnaiKabinetAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokGlavnaiKabinetAdapter(ArrayList<ZagolovokGlavnaiKabinet>zagolovokGlavnaiKabinets){
        this.zagolovokGlavnaiKabinets=zagolovokGlavnaiKabinets;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokGlavnaiKabinetAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokGlavnaiKabinetAdapter.ZagolovokGlavnaiKabinetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_glavnai_kabinet,parent,false);
        return new ZagolovokGlavnaiKabinetAdapter.ZagolovokGlavnaiKabinetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokGlavnaiKabinetAdapter.ZagolovokGlavnaiKabinetViewHolder holder, int position) {
        ZagolovokGlavnaiKabinet zagolovokGlavnaiKabinet=zagolovokGlavnaiKabinets.get(position);
        holder.Pole_Predmetov_Raspisanie.setText(zagolovokGlavnaiKabinet.getPolepredmetovraspisanie());
    }

    @Override
    public int getItemCount() {return zagolovokGlavnaiKabinets.size();}

    public class ZagolovokGlavnaiKabinetViewHolder extends RecyclerView.ViewHolder {
        private TextView Pole_Predmetov_Raspisanie;

        public ZagolovokGlavnaiKabinetViewHolder(@NonNull View itemView){
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