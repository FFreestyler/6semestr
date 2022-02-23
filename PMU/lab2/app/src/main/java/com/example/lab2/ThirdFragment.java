package com.example.lab2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ThirdFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TableLayout table = new TableLayout(getActivity());

        for (int i = 1; i <= 12; i++) {
            TableRow row = new TableRow(getActivity());
            for (int j = 1; j <= 5; j++) {
                Button button = new Button(getActivity());
                row.addView(button);
            }
            table.addView(row);
        }
        return table;
    }
}