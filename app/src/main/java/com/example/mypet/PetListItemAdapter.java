package com.example.mypet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class PetListItemAdapter extends RecyclerView.Adapter<PetListItemAdapter.ViewHolder> {
    private ArrayList<PetInfo> mPetList = new ArrayList<>();
    private String mPetUid = null;
    private Context mContext = null;

    public PetListItemAdapter(ArrayList<PetInfo> list, String petUid, Context context) {
        this.mPetList = list;
        this.mPetUid = petUid;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView birth;
        TextView category;
        TextView pet;
        View item;
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            birth = itemView.findViewById(R.id.text_birth);
            category = itemView.findViewById(R.id.text_category);
            pet = itemView.findViewById(R.id.text_dog_or_cat);
        }
    }

    @NonNull
    @Override
    public PetListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_pet_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mPetList.get(position).getName());
        holder.category.setText("종 : " + mPetList.get(position).getCategory());
        holder.birth.setText("생년월일 : " + mPetList.get(position).getBirth());
        String dOrc = mPetList.get(position).getPet();
        if(dOrc.equals("강아지"))
            holder.pet.setText("강아지");
        else
            holder.pet.setText("고양이");

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra("PETUID", mPetUid);
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() { return mPetList.size(); }
}
