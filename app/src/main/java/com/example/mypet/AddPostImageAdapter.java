package com.example.mypet;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AddPostImageAdapter extends RecyclerView.Adapter<AddPostImageAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onDeleteClick(View v, int position);
    }

    private ArrayList<Uri> mData = null;
    private Context mContext = null;
    private OnItemClickListener mListener = null;
    private Button btn_cancel_image;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public AddPostImageAdapter(ArrayList<Uri> list, Context context) { // 생성자
        mData = list;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ViewHolder(View itemView) { // 아이템 뷰 저장
            super(itemView);
            image = itemView.findViewById(R.id.add_image);
            btn_cancel_image = itemView.findViewById(R.id.btn_cancel_image);
            btn_cancel_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // 이미지 선택 취소 버튼
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION) {
                        if(mListener!=null) {
                            mListener.onDeleteClick(view, position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public AddPostImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰 홀더 객체 생성
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_add_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
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
