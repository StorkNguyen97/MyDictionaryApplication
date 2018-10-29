package com.atv.anhnd.mydictionaryapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.atv.anhnd.mydictionaryapplication.DataBaseHelper.TB_EN_VN;
import static com.atv.anhnd.mydictionaryapplication.DataBaseHelper.TB_VN_EN;

public class DictionaryFragment extends Fragment {

    private String value = "Linh cc";
    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView list_word;
    private ArrayList<String> memSource;
    private DataBaseHelper dataBaseHelper;
    private String table = TB_EN_VN;
    private boolean isSearch = false;

    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(view.getContext());

        list_word = view.findViewById(R.id.list_word);
        final String type = Global.getState(getActivity(), "dic_type");
        if (type != null && type.equals("ev")) {
            table = TB_EN_VN;
            memSource = dataBaseHelper.getWord(TB_EN_VN, 0);
        } else {
            table = TB_EN_VN;
            memSource = dataBaseHelper.getWord(TB_VN_EN, 0);
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, memSource);
        list_word.setAdapter(adapter);
        list_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.onItemClick(memSource.get(position));
            }
        });
        list_word.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (isSearch) return true;
                if (type != null && type.equals("ev")) {
                    memSource.addAll(dataBaseHelper.getWord(TB_EN_VN, page));
                } else {
                    memSource.addAll(dataBaseHelper.getWord(TB_VN_EN, page));
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void resetDataSource(ArrayList<String> source) {
        memSource = source;
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, memSource);
        list_word.setAdapter(adapter);
    }

    public void filterValue(String value) {
        if (value.trim().length() > 0) isSearch = true;
        else {
            isSearch = false;
            return;
        }
//        adapter.getFilter().filter(value);
//        int size = adapter.getCount();
//        for (int i = 0; i < size; i++) {
//            if (adapter.getItem(i).startsWith(value)) {
//                list_word.setSelection(i);
//                break;
//            }
//        }
        memSource.clear();
        memSource.addAll(dataBaseHelper.search(table, value));
        adapter.notifyDataSetChanged();
    }

    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }

    public void resetDataSource(String type) {
        memSource.clear();
        if (type.equals("ev")) {
            memSource.addAll(dataBaseHelper.getWord(TB_EN_VN, 0));
        } else {
            memSource.addAll(dataBaseHelper.getWord(TB_VN_EN, 0));
        }
        adapter.notifyDataSetChanged();
    }
}
