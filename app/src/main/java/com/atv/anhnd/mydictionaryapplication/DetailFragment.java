package com.atv.anhnd.mydictionaryapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private String value = "";
    private TextView word_detail, word_meaning;
    private ImageButton btn_mark;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getNewInstance(String value){
        DetailFragment fragment = new DetailFragment();
        fragment.value = value;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        word_detail = view.findViewById(R.id.work_detail);
        word_meaning = view.findViewById(R.id.word_meaning);
        btn_mark = view.findViewById(R.id.btn_mark);

        btn_mark.setTag(0);

        btn_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int)btn_mark.getTag();
                if (i == 0){
                    btn_mark.setImageResource(R.drawable.ic_bookmark_fill);
                    btn_mark.setTag(1);
                } else if (i == 1){
                    btn_mark.setImageResource(R.drawable.ic_bookmark_border);
                    btn_mark.setTag(0);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
