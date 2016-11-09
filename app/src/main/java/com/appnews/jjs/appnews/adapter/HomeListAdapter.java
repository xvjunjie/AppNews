package com.appnews.jjs.appnews.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appnews.jjs.appnews.R;

import java.util.List;

/**
 * Created by 15596 on 2016/10/17.
 */

public class HomeListAdapter extends BaseListAdapter<String> {

    private TextView item_text;

    public HomeListAdapter(Activity context, List<String> list) {
        super(context, list);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.item,null);
        }
        item_text = ViewHolder.get(convertView,R.id.item_text);
        item_text.setText(list.get(position));

        return convertView;
    }
}
