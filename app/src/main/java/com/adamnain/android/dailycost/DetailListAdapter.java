package com.adamnain.android.dailycost;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adamnain on 30/05/2017.
 */

public class DetailListAdapter extends BaseAdapter {

    private Context mContext;
    private List<detail> mDetailList;

    //constructor
    public DetailListAdapter(Context mContext, List<detail> mDetailList) {
        this.mContext = mContext;
        this.mDetailList = mDetailList;
    }

    @Override
    public int getCount() {
        return mDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //item_product_list = item_data_list
        View v = View.inflate(mContext, R.layout.item_data_list, null);
        TextView tvLDate = (TextView)v.findViewById(R.id.tv_ldate);
        TextView tvLIncome = (TextView)v.findViewById(R.id.tv_lincome);
        TextView tvLOutcome = (TextView)v.findViewById(R.id.tv_loutcome);
        TextView tvLNote = (TextView)v.findViewById(R.id.tv_lnote);

        //set text for textview
        tvLDate.setText(mDetailList.get(position).getDate());
        tvLIncome.setText(mDetailList.get(position).getIncome());
        tvLOutcome.setText(mDetailList.get(position).getOutcome());
        tvLNote.setText(mDetailList.get(position).getNote());

        //save product id to tag
        v.setTag(mDetailList.get(position).getId());
        return v;

    }
}
