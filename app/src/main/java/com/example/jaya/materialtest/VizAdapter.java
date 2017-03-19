package com.example.jaya.materialtest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jaya on 28/02/17.
 */

public class VizAdapter extends RecyclerView.Adapter<VizAdapter.MyViewHolder>{

    private LayoutInflater layoutInflater;
    List<Information> data = Collections.emptyList();
    Context context;

    public VizAdapter(Context context, List<Information> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data= data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row,parent,false);
        Log.d("VivZ" , "onCreateViewHolder called");
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Information current = data.get(position);
        Log.d("VivZ" , "onBindViewHolder called " + position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconID);
        holder.icon.setOnClickListener(mClickListener);
        holder.icon.setTag(holder);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyViewHolder holder = (MyViewHolder) view.getTag();
            int position = holder.getAdapterPosition();


            Toast.makeText(context,"Item at position " + position + " clicked",Toast.LENGTH_LONG).show();
        }

    };

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}
