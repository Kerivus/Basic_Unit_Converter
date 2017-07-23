package com.example.win10.unitconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ConversionActivity extends AppCompatActivity implements OnItemSelectedListener{
    public static final String SELECTED_ID = "Unit_Type_Id_Selected";
    public static final String SELECTED_NAME = "Unit_Name_Id_Selected";
    private double input=0,answer=0;
    private String fromUnit;
    private String toUnit;
    private String selected_unit = null;
    private TextView mTextView;
    private TextView mfromTextView;
    private EditText mEditText;
    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private Button mConvertButton;
    private TypeDataSource mTypeDataSource;
    private UnitDataSource mUnitDataSource;
    private HistoryDataSource mHistoryDataSource;
    private ConversionDataSource mConversionDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        mTypeDataSource = new TypeDataSource(this);
        mTypeDataSource.open();
        mUnitDataSource = new UnitDataSource(this);
        mUnitDataSource.open();
        mConversionDataSource = new ConversionDataSource(this);
        mConversionDataSource.open();
        mHistoryDataSource = new HistoryDataSource(this);
        mHistoryDataSource.open();

        long type_Id = getIntent().getLongExtra(SELECTED_ID, 0);

        fromUnitSpinner = (Spinner) findViewById(R.id.FromSpinner);
        fromUnitSpinner.setOnItemSelectedListener(this);
        toUnitSpinner = (Spinner) findViewById(R.id.ToSpinner);
        toUnitSpinner.setOnItemSelectedListener(this);

        List<String> Unit = new ArrayList<String>();

        if(type_Id != 0) {
            Unit = mUnitDataSource.getAllTypeUnitName(type_Id);
            getSupportActionBar().setTitle(mTypeDataSource.getTypeName(type_Id)+ " "+getString(R.string.Conversion));
        }
        else
        {
            selected_unit = getIntent().getStringExtra(SELECTED_NAME);
            type_Id = mUnitDataSource.getTypeId(selected_unit);
            getSupportActionBar().setTitle(mTypeDataSource.getTypeName(type_Id)+ " "+getString(R.string.Conversion));
            Unit = mUnitDataSource.getAllTypeUnitName(type_Id);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Unit);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromUnitSpinner.setAdapter(dataAdapter);
        toUnitSpinner.setAdapter(dataAdapter);


        if (selected_unit!= null) {
            int spinnerPosition = dataAdapter.getPosition(selected_unit);
            fromUnitSpinner.setSelection(spinnerPosition);
            toUnitSpinner.setSelection(spinnerPosition);
        }

        mfromTextView=(TextView)findViewById(R.id.fromSymbol) ;

        mTextView=(TextView)findViewById(R.id.AnswerConversion) ;
        mEditText=(EditText)findViewById(R.id.Input_number_edit_text) ;
        mConvertButton=(Button)findViewById(R.id.convert_button);
            mConvertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Retrieve data from database
                    try {
                        input = Double.parseDouble(mEditText.getText().toString());
                    }catch(NumberFormatException ex)
                    {
                        Toast.makeText(ConversionActivity.this,"Please enter number in the textbox",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (fromUnit == toUnit) {
                        answer = input;
                            mTextView.setText(String.valueOf(answer) + mUnitDataSource.getUnitSymbol(toUnit));
                    }
                    else {
                        Conversion converter = new Conversion();
                        long fromid =mUnitDataSource.getUnitId(fromUnit);
                        long toid = mUnitDataSource.getUnitId(toUnit);
                        converter = mConversionDataSource.findConversion(fromid, toid);
                        if((fromid == 41 && toid == 44)|| (fromid == 44 && toid == 41))
                            answer = (input+converter.getFromOffset()) * converter.getMultiplier() / (converter.getDivizor()*10) + converter.getToOffSet();
                        else
                        {
                            answer = (input+converter.getFromOffset()) * converter.getMultiplier() / converter.getDivizor() + converter.getToOffSet();
                        }
                        mTextView.setText(String.valueOf(answer)+ mUnitDataSource.getUnitSymbol(toUnit));
                        //Record history
                        mHistoryDataSource.insertHistory(fromUnit + " convert to "+ toUnit,
                                (mEditText.getText().toString()+ mUnitDataSource.getUnitSymbol(fromUnit))
                                        + " = "+String.valueOf(answer)+ mUnitDataSource.getUnitSymbol(toUnit));
                    }
                }
            });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId())
        {
            case R.id.FromSpinner:
                fromUnit = parent.getItemAtPosition(position).toString();
                mfromTextView.setText(mUnitDataSource.getUnitSymbol(fromUnit));
                break;

            case R.id.ToSpinner:
                toUnit = parent.getItemAtPosition(position).toString();
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent){
        //Do nothing
    }

    @Override
    public void onResume() {
        mUnitDataSource.open();
        mHistoryDataSource.open();
        mConversionDataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        mUnitDataSource.close();
        mHistoryDataSource.close();
        mConversionDataSource.close();
        super.onPause();
    }
}
