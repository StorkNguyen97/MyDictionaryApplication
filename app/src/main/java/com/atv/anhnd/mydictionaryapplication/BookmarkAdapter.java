package com.atv.anhnd.mydictionaryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BookmarkAdapter extends BaseAdapter {

    private ListItemListener listener;
    private ListItemListener listenerBtnDel;

    Context memContext;
    ArrayList<String> memSource;

    public BookmarkAdapter(Context context, String[] source) {
        this.memContext = context;
        this.memSource = new ArrayList<>(Arrays.asList(source));
    }

    @Override
    public int getCount() {
        return memSource.size();
    }

    @Override
    public Object getItem(int position) {
        return memSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(memContext).inflate(R.layout.bookmark_item, viewGroup, false);
            viewHolder.txtView = view.findViewById(R.id.txtWord);
            viewHolder.btnDel = view.findViewById(R.id.btn_del);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtView.setText(memSource.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                listener.onClickItem(position);
            }
        });

        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenerBtnDel != null)
                    listenerBtnDel.onClickItem(position);
            }
        });
        return view;
    }

    public void removeItem(int position){
        memSource.remove(position);
    }

    class ViewHolder {
        TextView txtView;
        ImageView btnDel;
    }

    public void setOnClickListener(ListItemListener listener){
        this.listener = listener;
    }

    public void setOnClickDelListener(ListItemListener listenerBtnDel){
        this.listenerBtnDel = listenerBtnDel;
    }
}
