package edu.ktu.guessthenumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends Activity {


    //private static final String PREFERENCES_NAME = "PlayerStatistics";
    private static final String KEY_PLAYS = "PlayerPlayCount";
    private static final String KEY_WINS = "PlayerWinCount";
    private static final String KEY_LOSE = "PlayerLostCount";
    private static final String PREFERENCES_NAME = "PlayerInfo";
    private static final String KEY_NAME = "PlayerName";
    private static final String KEY_RANGE_FROM = "PlayerRangeFrom";
    private static final String KEY_RANGE_TO = "PlayerRangeTo";
    private static final String KEY_TURNS = "MaxTurns";

    private int maxTurns = 7;
    private int currentTurn = 0;
    private int minNumber = 1;
    private int maxNumber = 100;
    private int randomNumber = -1;

    TextView turnsText;
    TextView rangeText;
    TextView resultText;
    EditText guessInput;
    Button guessBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        turnsText = findViewById(R.id.turns);
        rangeText = findViewById(R.id.guessRange);
        resultText = findViewById(R.id.guessResult);
        guessInput = findViewById(R.id.guessedNumber);
        guessBtn = findViewById(R.id.guessBtn);

        loadStatistics();
        turnsText.setText(getString(R.string.turns_left) + maxTurns);
        String rangeString = "Input a number between " + minNumber + " and " + maxNumber;
        rangeText.setText(rangeString);
        resultText.setText("");


        guessBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GuessNumber();
            }
        });

        Random rand = new Random();
        randomNumber = rand.nextInt(maxNumber - minNumber + 1) + minNumber;

        initializeSimpleAdapter();
    }

    void GuessNumber()
    {
        boolean hasWon = false;
        boolean hasLost = false;
        int guessResult = 0;
        int guessedNumber;

        String input = guessInput.getText().toString();
        if (input.isEmpty())
            guessedNumber = -1;
        else
            guessedNumber = Integer.parseInt(input);

        if(guessedNumber >= minNumber && guessedNumber <= maxNumber)
        {
            if (guessedNumber > randomNumber) {
                resultText.setText(R.string.number_too_big);
                guessResult = 1;
            } else if (guessedNumber < randomNumber) {
                resultText.setText(R.string.number_too_small);
                guessResult = -1;
            } else {
                hasWon = true;
            }
            guessInput.setText(null);
            currentTurn++;
            turnsText.setText(getString(R.string.turns_left) + (maxTurns - currentTurn));
            addToSimpleAdapter(guessedNumber, guessResult, maxTurns - currentTurn);
            if(maxTurns - currentTurn < 4)
                turnsText.setTextColor(getResources().getColor(R.color.yellow));
            if (currentTurn == maxTurns)
            {
                hasLost = true;
            }
            if (hasWon || hasLost) {
                Intent intent = new Intent(this, GameOverActivity.class);
                intent.putExtra("HAS_WON", hasWon);
                intent.putExtra("NUMBER", randomNumber);
                Log.d("TAG", Integer.toString(maxTurns - currentTurn));
                intent.putExtra("TURNS", maxTurns - currentTurn);
                startActivity(intent);
                finish();
            }
        }
        else
        {
            resultText.setTextColor(getResources().getColor(R.color.yellow));
            resultText.setText("Please enter a valid number!");
            guessInput.setText(null);
        }
    }

    SimpleAdapter simpleAdapter;
    List<Map<String, String>> simpleData;

    void initializeSimpleAdapter()
    {
        simpleData = new ArrayList<>();
        String[] from = {"NUMBER", "RESULT", "TURNS"};

        //int[] to = {android.R.id.text1};
        int[] to = {R.id.list_guessedNumber, R.id.list_result, R.id.list_turns};
        simpleAdapter = new SimpleAdapter(this, simpleData, R.layout.guessed_number_list_item, from, to);

        ListView listView = findViewById(R.id.resultsList);
        listView.setAdapter(simpleAdapter);

    }

    void addToSimpleAdapter(int guessedNumber, int result, int turnsLeft)
    {

        String numberString = Integer.toString(guessedNumber);
        String resultString = "";
        if(result > 0)
        {
            resultString += " Guessed number was too big.";
        }
        else if (result < 0)
        {
            resultString += " Guessed number was too small.";
        }
        String turnsString = " Turns left: " + turnsLeft;

        Map<String, String> data = new HashMap<>();
        data.put("NUMBER", numberString);
        data.put("RESULT", resultString);
        data.put("TURNS", turnsString);
        simpleData.add(data);
        simpleAdapter.notifyDataSetChanged();

    }

    protected void loadStatistics()
    {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        minNumber = preferences.getInt(KEY_RANGE_FROM, 1);
        maxNumber = preferences.getInt(KEY_RANGE_TO, 100);
        maxTurns = preferences.getInt(KEY_TURNS, 7);
    }
}
