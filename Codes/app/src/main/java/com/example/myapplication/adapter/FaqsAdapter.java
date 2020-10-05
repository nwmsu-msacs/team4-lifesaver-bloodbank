package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.myapplication.R;

import java.util.List;


public class FaqsAdapter extends BaseAdapter {
    Context context;
    List<String> list,listAns;

    public FaqsAdapter(Context context, List<String> list, List<String> listAns) {
        this.context = context;
        this.list = list;
        this.listAns = listAns;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
           LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.faqs_item, parent, false);
        }
        TextView question=(TextView)convertView.findViewById(R.id.question);
        TextView answer=(TextView)convertView.findViewById(R.id.answer);
        question.setText(Html.fromHtml(list.get(position)));
        answer.setText(listAns.get(position));
        return null;
    }
}
