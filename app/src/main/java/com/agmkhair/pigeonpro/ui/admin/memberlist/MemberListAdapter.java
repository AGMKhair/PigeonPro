package com.agmkhair.pigeonpro.ui.admin.memberlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.agmkhair.pigeonpro.R;
import com.agmkhair.pigeonpro.ui.model.Member;

import java.util.List;


public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder>
{

    private List<Member> list;
    private Context context;

    public MemberListAdapter(List<Member>  list, Context context) {
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

    public void setList(List<Member>  list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
