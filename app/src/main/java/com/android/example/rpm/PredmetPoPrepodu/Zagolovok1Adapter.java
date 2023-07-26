package com.android.example.rpm.PredmetPoPrepodu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class Zagolovok1Adapter extends RecyclerView.Adapter<Zagolovok1Adapter.Zagolovok1ViewHolder>{

    private ArrayList<Zagolovok1> zagolovok1s;
    private Zagolovok1Adapter.OnNoteClickListener onNoteClickListener;

    public Zagolovok1Adapter(ArrayList<Zagolovok1>zagolovok1s){
        this.zagolovok1s=zagolovok1s;
    }

    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(Zagolovok1Adapter.OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public Zagolovok1Adapter.Zagolovok1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_zagolovok1,parent,false);
        return new Zagolovok1Adapter.Zagolovok1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Zagolovok1Adapter.Zagolovok1ViewHolder holder, int position) {
        Zagolovok1 zagolovok1=zagolovok1s.get(position);
        holder.Prepod.setText(zagolovok1.getPrepod());
    }

    @Override
    public int getItemCount() {return zagolovok1s.size();}

    public class Zagolovok1ViewHolder extends RecyclerView.ViewHolder {
        private TextView Prepod;

        public Zagolovok1ViewHolder(@NonNull View itemView){
            super(itemView);
            Prepod=itemView.findViewById(R.id.Prepod);
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
