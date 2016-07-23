package com.natashahaggard.pidgeycalculator;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.pokemon,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        TextView myText= (TextView)view;
        Toast.makeText(this,"You Selected "+myText.getText(), Toast.LENGTH_SHORT).show();
    }

    // onNothingSelected is called when your current selection disappears due to some event like touch getting activated or the adapter becoming empty
    @Override
    public void onNothingSelected(AdapterView<?> adapterView){
    }

     /*
     step 1 create the data source
     step 2 define the appearance layout file through which the adapter will put data inside the spinner
     step 3 define what to do when the user clicks on the spinner using the OnItemsSelectedListener
      */
}