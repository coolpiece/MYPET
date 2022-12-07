package com.example.mypet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    public List<Petinfo> petinfoList;
    private ArrayList<Petinfo> arrayList = new ArrayList<Petinfo>();
    private Activity activity;

  /*  static class CustomViewHolder extends RecyclerView.ViewHolder {

        CustomViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
*/
    public CustomAdapter(List<Petinfo> petinfoList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.petinfoList = petinfoList;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final CustomViewHolder customViewHolder = new CustomViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        return customViewHolder;*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder((CardView) view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.CustomViewHolder holder, int position) {
      /*  CardView cardView = holder.cardView;
        ImageView profile = cardView.findViewById(R.id.profile);
        TextView text_name = cardView.findViewById(R.id.text_name);
        TextView text_pet = cardView.findViewById(R.id.text_pet);
        TextView text_birth = cardView.findViewById(R.id.text_birth);*/

        Petinfo petinfo = arrayList.get(position);
      /*  if(arrayList.get(position).getProfile() != null){
            Glide.with(activity).load(arrayList.get(position).getProfile()).centerCrop().override(500).into(profile);
        }
        text_name.setText(petinfo.getName());
        text_pet.setText(petinfo.getPet());
        text_birth.setText(petinfo.getBirth());*/
        holder.name.setText(petinfo.getName());
        holder.category.setText(petinfo.getCategory());
        holder.birth.setText(petinfo.getBirth());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        View view;

        public TextView name;
        public TextView birth;
        public TextView category;
        public CustomViewHolder(View itemView){
            super(itemView);
            view=itemView;

            name=view.findViewById(R.id.text_name);
            birth=view.findViewById(R.id.text_birth);
            category=view.findViewById(R.id.text_pet);
        }
    }
/*
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


        }*/


}
