package com.natashahaggard.pidgeycalculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;

    // Declare variables
    EditText howManyPokemon; // # of pokemon from user input
    EditText howManyCandies; // # of candies from user input
    TextView evolutionsResult; // Display how many evolutions are possible
    Button button_calculate; // Calculate button
    Button button_reset; // Reset button

    int numOfPokemon, numOfCandies, numOfEvolutions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        // Read the values entered in the Text Views using an id set in XML code
        howManyPokemon = (EditText)findViewById(R.id.pokemonEditText);
        howManyCandies = (EditText)findViewById(R.id.candiesEditText);
        button_calculate = (Button)findViewById(R.id.button_calculate);
        button_reset = (Button)findViewById(R.id.button_reset);

        // Clear all text fields
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2){
                howManyPokemon.setText("");
                howManyCandies.setText("");
            }
        });

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.pokemon_12common,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    // Calculate function
    private void calculate(){
        try {
            numOfPokemon = Integer.parseInt(howManyPokemon.getText().toString());
            numOfCandies = Integer.parseInt(howManyCandies.getText().toString());
        }
        catch(NumberFormatException e){// if it's empty or non-numeric
            numOfPokemon = 0;
            numOfCandies = 0;
        }
        numOfEvolutions = numOfPokemon + numOfCandies;
    }

    // You selected such-and-such Pokemon message
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        TextView myText= (TextView)view;
        Toast.makeText(this,"You Selected "+myText.getText(), Toast.LENGTH_SHORT).show();
    }

    // onNothingSelected is called when your current selection disappears due to some event like touch getting activated or the adapter becoming empty
    @Override
    public void onNothingSelected(AdapterView<?> adapterView){
    }

    // Display a dialog with calculation results
    public void showAlert(View view) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        calculate();
        myAlert.setMessage("Number of Evolutions is " + numOfEvolutions)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }
}