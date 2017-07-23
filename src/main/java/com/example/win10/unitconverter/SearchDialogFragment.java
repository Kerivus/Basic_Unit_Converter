package com.example.win10.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class SearchDialogFragment extends DialogFragment {
    public static final String SELECTED_NAME = "Unit_Name_Id_Selected";
    private UnitDataSource mUnitDataSource;
    private EditText mEditText;
    private ListView mListView;
    private String mUnit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_dialogfragment, container,
                false);

        getDialog().setTitle("Search Unit");
        mUnitDataSource = new UnitDataSource(getContext());
        mUnitDataSource.open();

        mEditText = (EditText) rootView.findViewById(R.id.search);
        mListView = (ListView) rootView.findViewById(R.id.searchlist);
        mListView.setTextFilterEnabled(true);

        List<String> values = mUnitDataSource.getAllUnitName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(adapter);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) mListView.getAdapter();
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String unit = parent.getItemAtPosition(position).toString();
                    Intent intent = new Intent(getActivity(), ConversionActivity.class);
                    intent.putExtra(SELECTED_NAME, unit);
                    startActivity(intent);
                    getDialog().dismiss();
            }
        });
        return rootView;
    }
}


