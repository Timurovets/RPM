package com.android.example.rpm.Prepod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokPrepodaAdapter extends RecyclerView.Adapter<ZagolovokPrepodaAdapter.ZagolovokPrepodaViewHolder>{

    private ArrayList<ZagolovokPrepoda>zagolovokPrepodas;
    private OnNoteClickListener onNoteClickListener;

    public ZagolovokPrepodaAdapter(ArrayList<ZagolovokPrepoda>zagolovokPrepodas){
        this.zagolovokPrepodas=zagolovokPrepodas;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokPrepodaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_prepoda,parent,false);
        return new ZagolovokPrepodaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokPrepodaViewHolder holder, int position) {
        ZagolovokPrepoda zagolovokPrepoda=zagolovokPrepodas.get(position);
        holder.Familia.setText(zagolovokPrepoda.getFam());
        holder.ImaOtchestvo.setText(zagolovokPrepoda.getImaotchestvo());
        holder.IDPolzovatela.setText(zagolovokPrepoda.getIdPolzovatela());
    }

    @Override
    public int getItemCount() {return zagolovokPrepodas.size();}

    public class ZagolovokPrepodaViewHolder extends RecyclerView.ViewHolder {
        private TextView Familia;
        private TextView ImaOtchestvo;
        private TextView IDPolzovatela;

        public ZagolovokPrepodaViewHolder(@NonNull View itemView){
            super(itemView);
            Familia=itemView.findViewById(R.id.Familia);
            ImaOtchestvo=itemView.findViewById(R.id.ImaOtch);
            IDPolzovatela=itemView.findViewById(R.id.IDPolzovatela);
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

