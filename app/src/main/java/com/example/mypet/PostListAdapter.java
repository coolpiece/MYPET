package com.example.mypet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    private ArrayList<AddPost> mList = null;
    private Context mContext = null;

    public PostListAdapter(ArrayList<AddPost> list, Context context) {
        mList = list;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView Content;
        ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Content = itemView.findViewById(R.id.content);
        }
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_post_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Title.setText(mList.get(position).getTitle());
        holder.Content.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() { return mList.size(); }
}
