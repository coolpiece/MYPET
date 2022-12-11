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


import java.util.ArrayList;
import java.util.List;

public class PetselectAdapter extends RecyclerView.Adapter<PetselectAdapter.ViewHolder> {
    private ArrayList<Petinfo> mInfo = null;
    private Context mContext = null;


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mCategory;
        TextView mBirth;
        ImageView mProfile;
        ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.text_name);
            mCategory = itemView.findViewById(R.id.text_pet);
            mBirth = itemView.findViewById(R.id.text_birth);
            mProfile = itemView.findViewById(R.id.profile);
        }
    }

    public  PetselectAdapter(ArrayList<AddPost> info, Context context){
        mInfo = info;
        mContext = context;
    }

    @NonNull
    @Override
    public PetselectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        PetselectAdapter.ViewHolder viewHolder = new PetselectAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PetselectAdapter.ViewHolder holder, int position) {
        holder.mName.setText(mInfo.get(position).getName());
        holder.mCategory.setText(mInfo.get(position).getCategory());
        holder.mBirth.setText(mInfo.get(position).getBirth());
       // holder.mProfile.setI(mInfo.ge)
    }

    @Override
    public int getItemCount() { return mInfo.size(); }

}