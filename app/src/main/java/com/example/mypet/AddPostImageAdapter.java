package com.example.mypet;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class AddPostImageAdapter extends RecyclerView.Adapter<AddPostImageAdapter.ViewHolder> {
    private ArrayList<Uri> mData = null;
    private Context mContext = null;

    AddPostImageAdapter(ArrayList<Uri> list, Context context) { // 생성자
        mData = list;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // 아이템 뷰 저장
        ImageView image;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.add_image);
        }
    }

    @Override
    public AddPostImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰 홀더 객체 생성
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_add_image, parent, false);
        AddPostImageAdapter.ViewHolder viewHolder = new AddPostImageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddPostImageAdapter.ViewHolder holder, int position) { // position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
        Uri image_uri = mData.get(position);
        Glide.with(mContext)
                .load(image_uri)
                .into(holder.image);
    }

    @Override
    public int getItemCount() { // 전체 데이터 개수 리턴
        return mData.size();
    }
}
