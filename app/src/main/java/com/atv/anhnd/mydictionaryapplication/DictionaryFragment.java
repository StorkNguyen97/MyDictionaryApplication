package com.atv.anhnd.mydictionaryapplication;

import android.content.Context;
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

public class DictionaryFragment extends Fragment {

    private String value = "Hello Linh";
    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView list_word;
    private ArrayList<String> memSource = new ArrayList<String>();


    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        list_word = view.findViewById(R.id.list_word);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, memSource);
        list_word.setAdapter(adapter);
        list_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.onItemClick(memSource.get(position));
            }
        });
    }

    public void resetDataSource(ArrayList<String> source){
        memSource = source;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, memSource);
        list_word.setAdapter(adapter);
    }

    public void filterValue(String value) {
        adapter.getFilter().filter(value);
//        int size = adapter.getCount();
//        for (int i = 0; i < size; i++) {
//            if (adapter.getItem(i).startsWith(value)) {
//                list_word.setSelection(i);
//                break;
//            }
//        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }

}
