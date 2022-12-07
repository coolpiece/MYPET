package com.example.mypet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    ArrayList<Petinfo> arrayList;
    Context context;


    public CustomAdapter(ArrayList<Petinfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
       Glide.with(holder.itemView)
               .load(arrayList.get(position).getProfile())
               .into(holder.profile);
       holder.text_name.setText(arrayList.get(position).getName());
       holder.text_pet.setText(arrayList.get(position).getPet());
       holder.text_birth.setText(String.valueOf(arrayList.get(position).getBirth()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView text_name;
        TextView text_pet;
        TextView text_birth;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profile = itemView.findViewById(R.id.profile);
            this.text_name = itemView.findViewById(R.id.text_name);
            this.text_pet = itemView.findViewById(R.id.text_pet);
            this.text_birth = itemView.findViewById(R.id.text_birth);


        }
    }

}
