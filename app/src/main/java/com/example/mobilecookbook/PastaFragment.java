package com.example.mobilecookbook;

import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.fragment.app.ListFragment;

public class PastaFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // this is responsible for the displayed content // paste is strings from res/strings.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.pasta)
        );
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
