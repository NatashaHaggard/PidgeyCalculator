package com.natashahaggard.pidgeycalculator;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    private TextView pokemonTextView;
    private TextView candiesTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.pokemon_12common,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // get references to programmatically manipulated TextViews
       pokemonTextView = (TextView) findViewById(R.id.pokemonTextView);
       candiesTextView = (TextView) findViewById(R.id.candiesTextView);

        // set pokemonEditText's Text Watcher
        EditText pokemonEditText =
                (EditText) findViewById(R.id.pokemonEditText);
      // pokemonEditText.addTextChangedListener(pokemonEditTextWatcher);

        // set candiesEditText's Text Watcher
        EditText candiesEditText =
                (EditText) findViewById(R.id.candiesEditText);
    //   candiesEditText.addTextChangedListener(candiesEditTextWatcher);
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

}