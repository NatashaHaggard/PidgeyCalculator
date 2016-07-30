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
    Integer candiesPerEvolution; // # of candies required per evolution
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

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.pokemon, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }


    // You selected such-and-such Pokemon message
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        TextView myText= (TextView)view; // create a new TextView object called myText
        Toast.makeText(this,"You Selected "+myText.getText(), Toast.LENGTH_SHORT).show(); // Display the message in the Toast widget

        int value = spinner.getSelectedItemPosition();
        if ((value >= 0) && (value <= 2)) {
            candiesPerEvolution = 12;
            calculate(candiesPerEvolution);
        }
        else if (((value >= 3) && (value <= 5)) || ((value >=9) && (value <=20))) {
            candiesPerEvolution = 25;
            calculate(candiesPerEvolution);
        }
        else if (((value >= 6) && (value <= 8)) || ((value >=21) && (value <=55))) {
            candiesPerEvolution = 50;
            calculate(candiesPerEvolution);
        }
        else if ((value >= 56) && (value <= 68)) {
            candiesPerEvolution = 100;
            calculate(candiesPerEvolution);
        }
        else if (value == 69) {
            candiesPerEvolution = 400;
            calculate(candiesPerEvolution);
        }
    }

    // Calculate function
    private Integer[] calculate(Integer candiesPerEvolution){
        try {
            numOfPokemon = Integer.parseInt(howManyPokemon.getText().toString());
            numOfCandies = Integer.parseInt(howManyCandies.getText().toString());
        }
        catch(NumberFormatException e){// if it's empty or non-numeric
            numOfPokemon = 0;
            numOfCandies = 0;
        }

        // Declare and initialize variables
        Integer candiesRequired;
        Integer transferPokemon = 0;
        Integer evolvePokemon = 0;
        Integer gainXP = 0;
        Integer howManyMinutes = 0;
        Integer pokemonLeftOver = 0;
        Integer candiesLeftOver = 0;
        Integer candiesNeededFor1Evolution = 0;

        // Multiply the number of Pokemon by candiesPerEvolution to see how many candies will be required to evolve all the Pokemon
        candiesRequired = numOfPokemon * candiesPerEvolution; // # of candies required to evolve all your pidgeys

        if (numOfCandies >= candiesPerEvolution){ // check to see if you have enough candy
            if (numOfPokemon < 60){
                transferPokemon = 60 - numOfPokemon;
            }
            evolvePokemon = numOfCandies / candiesPerEvolution;
            gainXP = evolvePokemon * 1000; // 1000 with Lucky Egg, 500 without
            howManyMinutes = (evolvePokemon * 30)/60;
            candiesLeftOver = numOfCandies - (evolvePokemon*candiesPerEvolution);
            pokemonLeftOver = numOfPokemon - evolvePokemon;
        }

        else if (numOfCandies < candiesRequired){
            candiesNeededFor1Evolution = Math.abs(numOfCandies-candiesRequired); // get absolute value
            transferPokemon = (candiesPerEvolution * 59) + candiesNeededFor1Evolution;
            candiesLeftOver = numOfCandies;
            pokemonLeftOver = numOfPokemon;
        }

       Integer[] calculationResults = new Integer[6];
        calculationResults[0] = transferPokemon;
        calculationResults[1] = evolvePokemon;
        calculationResults[2] = gainXP;
        calculationResults[3] = howManyMinutes;
        calculationResults[4] = pokemonLeftOver;
        calculationResults[5] = candiesLeftOver;

        return calculationResults;
    }

    public StringBuilder displayResults(Integer[] calculationResults){
        StringBuilder displayResults = new StringBuilder();
        displayResults.append("Recommendation:");
        displayResults.append("\n");
        displayResults.append("You should transfer ");
        displayResults.append(calculationResults[0]); //transferPokemon
        displayResults.append(" Pokemon before activating your Lucky Egg");
        displayResults.append("\n\n");
        displayResults.append("Right now, you will be able to evolve ");
        displayResults.append(calculationResults[1]); //evolvePokemon
        displayResults.append(" Pokemon, gaining " );
        displayResults.append(calculationResults[2]); //gainXP
        displayResults.append(" XP (with Lucky Egg) ");
        displayResults.append(" and ");
        displayResults.append(calculationResults[2] / 2); //gainXP without Lucky Egg
        displayResults.append(" XP (without Lucky Egg) ");
        displayResults.append("\n\n");
        displayResults.append("On average, it will take ");
        displayResults.append(calculationResults[3]); //howManyMinutes
        displayResults.append(" minute(s) to evolve your Pokemon");
        displayResults.append("\n\n");
        displayResults.append("You will have ");
        displayResults.append(calculationResults[4]); //pokemonLeftOver
        displayResults.append(" Pokemon and ");
        displayResults.append(calculationResults[5]); //candiesLeftOver
        displayResults.append(" candies left over");

        return displayResults;
    }

    // onNothingSelected is called when your current selection disappears due to some event like touch getting activated or the adapter becoming empty
    @Override
    public void onNothingSelected(AdapterView<?> adapterView){
    }

    // Display a dialog with calculation results
    public void showAlert(View view) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        StringBuilder text = displayResults(calculate(candiesPerEvolution));
        myAlert.setMessage(text.toString())
                .setPositiveButton("Okay, thanks!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }
}