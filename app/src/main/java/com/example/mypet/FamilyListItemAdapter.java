package com.example.mypet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FamilyListItemAdapter extends BaseAdapter {
    ArrayList<FamilyListItem> items = new ArrayList<FamilyListItem>();
    Context context;

    @Override // ArrayList의 크기 리턴. 아이템의 수.
    public int getCount() { return items.size(); }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        FamilyListItem listItem = items.get(position);

        if(convertView == null) { // item_family_list를 inflate해서 convertview를 참조
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_family_list, parent, false);
        }

        // 화면에 보여질 데이터를 참조
        TextView nameText = convertView.findViewById(R.id.name);
        TextView emailText = convertView.findViewById(R.id.email);

        //데이터 set
        nameText.setText(listItem.getName());
        emailText.setText(listItem.getEmail());

        return convertView; // convertView View 객체 리턴
    }

    public void addItem(FamilyListItem item) {
        items.add(item);
    }
}
