package com.android.example.rpm.Predmet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ZagolovokPredmetAdapter extends RecyclerView.Adapter<ZagolovokPredmetAdapter.ZagolovokPredmetViewHolder>{

    private ArrayList<ZagolovokPredmet> zagolovokPredmets;
    private ZagolovokPredmetAdapter.OnNoteClickListener onNoteClickListener;

    public ZagolovokPredmetAdapter(ArrayList<ZagolovokPredmet>zagolovokPredmets){
        this.zagolovokPredmets=zagolovokPredmets;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(ZagolovokPredmetAdapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ZagolovokPredmetAdapter.ZagolovokPredmetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok_predmet,parent,false);
        return new ZagolovokPredmetAdapter.ZagolovokPredmetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZagolovokPredmetAdapter.ZagolovokPredmetViewHolder holder, int position) {
        ZagolovokPredmet zagolovokPredmet=zagolovokPredmets.get(position);
        holder.NazvaniePredmet.setText(zagolovokPredmet.getNazvaniepredmet());
    }

    @Override
    public int getItemCount() {return zagolovokPredmets.size();}

    public class ZagolovokPredmetViewHolder extends RecyclerView.ViewHolder {
        private TextView NazvaniePredmet;

        public ZagolovokPredmetViewHolder(@NonNull View itemView){
            super(itemView);
            NazvaniePredmet=itemView.findViewById(R.id.NazvaniePredmeta);
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
