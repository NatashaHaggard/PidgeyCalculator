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
    int candiesPerEvolution; // # of candies required per evolution
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
           // calculate();
        }
        else if (((value >= 3) && (value <= 5)) || ((value >=9) && (value <=20))) {
            candiesPerEvolution = 25;
            //calculate();
        }
        else if (((value >= 6) && (value <= 8)) || ((value >=21) && (value <=55))) {
            candiesPerEvolution = 50;
           // calculate();
        }
        else if ((value >= 56) && (value <= 68)) {
            candiesPerEvolution = 100;
           // calculate();
        }
        else if (value == 69) {
            candiesPerEvolution = 400;
           // calculate();
        }
    }

    // You should transfer this many Pokemon before activating your Lucky Egg
    private int transferPokemon(){
        int totalCandiesNeeded;
        int transferPokemonForMoreCandy;
        totalCandiesNeeded = 60 * candiesPerEvolution;
        transferPokemonForMoreCandy = totalCandiesNeeded - numOfCandies;
        if (transferPokemonForMoreCandy < 0){
            transferPokemonForMoreCandy = 0;
        }
        return transferPokemonForMoreCandy;
    }

    // You should get this many additional Pokemon to evolve before activating your Lucky Egg
    private int evolveMorePokemon(){
        int evolveMorePokemon;
        evolveMorePokemon = 60 - numOfPokemon;
        if (evolveMorePokemon < 0){
            evolveMorePokemon = 0;
        }
        return evolveMorePokemon;
    }

    // This is how many Pokemon you can evolve right now
    private int evolvePokemonNow(){
        int evolvePokemonNow;
        evolvePokemonNow = numOfCandies / candiesPerEvolution;
        if (evolvePokemonNow > numOfPokemon){
            evolvePokemonNow = numOfPokemon;
        }
        if (evolvePokemonNow < 0){
            evolvePokemonNow = 0;
        }
        return evolvePokemonNow;
    }

    // This is how much XP you will gain
    private int gainXP(int evolvePokemonNow){
        int gainXP;
        gainXP= evolvePokemonNow * 500;
        return gainXP;
    }

    // This is how much XP you will gain if you use the Lucky Egg
    private int gainXP_LuckyEgg(int evolvePokemonNow){
        int gainXP_LuckyEgg;
        gainXP_LuckyEgg = evolvePokemonNow * 1000;
        return gainXP_LuckyEgg;
    }

    // How much candies will you have left over after doing current evolutions
    private int candiesLeftOver(int evolvePokemonNow){
        int candiesLeftOver;
        candiesLeftOver = numOfCandies - (evolvePokemonNow*candiesPerEvolution);
        if (candiesLeftOver < 0){
            candiesLeftOver = 0;
        }
        return candiesLeftOver;
    }

    // How much pokemon will you have left over after doing current evolutions
    private int pokemonLeftOver(int evolvePokemonNow){
        int pokemonLeftOver;
        pokemonLeftOver = numOfPokemon - evolvePokemonNow;
        if (pokemonLeftOver < 0){
            pokemonLeftOver = 0;
        }
        return pokemonLeftOver;
    }

    // How many minutes will the current evolutions take
    private float howManyMinutes(int evolvePokemonNow){
        float howManyMinutes = 0;
        if (evolvePokemonNow != 0) {
            howManyMinutes = ((float)evolvePokemonNow * 30f) / 60f;
        }
        return howManyMinutes;
    }

    // # of candies required to evolve the amount of Pokemon you currently have
    private int candiesRequiredForCurrentPokemon(Integer numOfPokemon, Integer candiesPerEvolution){
        int candiesRequired;
        candiesRequired = numOfPokemon * candiesPerEvolution;
        return candiesRequired;
    }

    // Calculate function
    private StringBuilder calculate(){
        try {
            numOfPokemon = Integer.parseInt(howManyPokemon.getText().toString());
            numOfCandies = Integer.parseInt(howManyCandies.getText().toString());
        }
        catch(NumberFormatException e){// if it's empty or non-numeric
            numOfPokemon = 0;
            numOfCandies = 0;
        }

        int pokemonCount = evolvePokemonNow();

        return displayResults(
        transferPokemon(),
        evolveMorePokemon(),
        pokemonCount,
        gainXP(pokemonCount),
        gainXP_LuckyEgg(pokemonCount),
        candiesLeftOver(pokemonCount),
        pokemonLeftOver(pokemonCount),
        howManyMinutes(pokemonCount),
        candiesRequiredForCurrentPokemon(numOfPokemon, pokemonCount));

    }

    public StringBuilder displayResults(int transferPokemon, int evolveMorePokemon, int evolvePokemonNow, int gainXP, int gainXP_LuckyEgg,
                                        int candiesLeftOver, int pokemonLeftOver, float howManyMinutes, int candiesRequiredForCurrentPokemon){
        StringBuilder displayResults = new StringBuilder();
        displayResults.append("Lucky Egg Recommendation");
        displayResults.append("\n");

        displayResults.append("Do not use any Lucky Eggs until you can evolve at least ");
        displayResults.append(evolveMorePokemon); //transferPokemon
        displayResults.append(" more Pokemon");
        displayResults.append("\n\n");

        displayResults.append("This will require an additional ");
        displayResults.append(transferPokemon);
        displayResults.append(" candies");
        displayResults.append("\n\n");

        displayResults.append("Right now, you will be able to evolve ");
        displayResults.append(evolvePokemonNow);
        displayResults.append(" Pokemon");
        displayResults.append("\n\n");

        displayResults.append("If you evolve your Pokemon now, you will gain " );
        displayResults.append(gainXP_LuckyEgg); //gainXP
        displayResults.append(" XP (with Lucky Egg) ");
        displayResults.append(" and ");
        displayResults.append(gainXP); //gainXP without Lucky Egg
        displayResults.append(" XP (without Lucky Egg) ");
        displayResults.append("\n\n");

        displayResults.append("On average, it will take ");
        displayResults.append(howManyMinutes); //howManyMinutes
        displayResults.append(" minute(s) to evolve your Pokemon");
        displayResults.append("\n\n");

        displayResults.append("You will have ");
        displayResults.append(pokemonLeftOver); //pokemonLeftOver
        displayResults.append(" Pokemon and ");
        displayResults.append(candiesLeftOver); //candiesLeftOver
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
        StringBuilder text = calculate();
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