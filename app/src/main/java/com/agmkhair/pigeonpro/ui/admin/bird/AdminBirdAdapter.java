package com.agmkhair.pigeonpro.ui.admin.bird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.model.Birds;

import java.util.List;

public class AdminBirdAdapter  extends RecyclerView.Adapter<AdminBirdAdapter.MyViewHolder> {

    private List<Birds> list;
    private Context context;

    public AdminBirdAdapter(List<Birds> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.model_club_pigeon_show,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(List<Birds> birds) {
        this.list = birds;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
