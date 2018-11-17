package com.atv.anhnd.mydictionaryapplication;

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
    private DataBaseHelper dataBaseHelper;
    private String tu;
    private String nghia;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    //Init Detail Fragment Screen
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(view.getContext());
        tu = getArguments().getString("tu");
        nghia = getArguments().getString("nghia");

        btn_mark = view.findViewById(R.id.btn_mark);

        boolean isMask = dataBaseHelper.isWordMark(new Word(tu, nghia));
        if (isMask) {
            btn_mark.setImageResource(R.drawable.ic_bookmark_fill);
            btn_mark.setTag(1);
        } else {
            btn_mark.setImageResource(R.drawable.ic_bookmark_border);
            btn_mark.setTag(0);
        }

        word_detail = view.findViewById(R.id.work_detail);
        word_detail.setText(tu);
        word_meaning = view.findViewById(R.id.word_meaning);
        word_meaning.setText(nghia);

        btn_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) btn_mark.getTag();
                if (i == 0) {
                    btn_mark.setImageResource(R.drawable.ic_bookmark_fill);
                    btn_mark.setTag(1);
                    dataBaseHelper.addBookmark(new Word(tu, nghia));
                } else if (i == 1) {
                    btn_mark.setImageResource(R.drawable.ic_bookmark_border);
                    btn_mark.setTag(0);
                    dataBaseHelper.removeBookmark(new Word(tu, nghia));
                }
            }
        });
    }
}
