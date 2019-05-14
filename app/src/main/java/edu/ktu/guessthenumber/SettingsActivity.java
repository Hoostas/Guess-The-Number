package edu.ktu.guessthenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    private static final String PREFERENCES_NAME = "PlayerInfo";
    private static final String KEY_NAME = "PlayerName";
    private static final String KEY_AGE = "PlayerAge";
    private static final String KEY_RADIO = "PlayerRadio";
    private static final String KEY_RANGE_FROM = "PlayerRangeFrom";
    private static final String KEY_RANGE_TO = "PlayerRangeTo";
    private static final String KEY_PLAYS = "PlayerPlayCount";
    private static final String KEY_WINS = "PlayerWinCount";
    private static final String KEY_LOSE = "PlayerLostCount";
    private static final String KEY_STREAK = "CurrentWinStreak";
    private static final String KEY_LONGEST = "LongestWinStreak";
    private static final String KEY_TURNS = "MaxTurns";
    private static final String KEY_FIRST = "FirstGuess";
    private static final String KEY_LAST = "LastGuess";
    private static final String KEY_GODMODE = "GodMode";


    EditText nameField;
    EditText ageField;
    EditText rangeFrom;
    EditText rangeTo;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CheckBox resetStatistics;
    RadioButton godModeRadio;
    RadioButton easyRadio;
    RadioButton mediumRadio;
    RadioButton hardRadio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        nameField = findViewById(R.id.nameEditText);
        ageField = findViewById(R.id.ageEditText);
        rangeFrom = findViewById(R.id.rangFrom);
        rangeTo = findViewById(R.id.rangeTo);
        radioGroup = findViewById(R.id.radioGroup);
        godModeRadio = findViewById(R.id.godModeRadioBtn);
        resetStatistics = findViewById(R.id.resetStatisticsCBox);
        //radioGroup.check(easyRadio.); //TODO
        loadSettings();
        resetStatistics.setText("Reset " + nameField.getText() + " statistics and achievements");
    }

    protected void loadSettings()
    {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String playerName = preferences.getString(KEY_NAME, "Player");
        int playerAge = preferences.getInt(KEY_AGE, 0);
        int playerRadio = preferences.getInt(KEY_RADIO, 0);
        int rangeFromValue = preferences.getInt(KEY_RANGE_FROM, 1);
        int rangeToValue = preferences.getInt(KEY_RANGE_TO, 100);
        boolean godMode = preferences.getBoolean(KEY_GODMODE, false);



        nameField.setText(playerName);
        ageField.setText(Integer.toString(playerAge));
        radioGroup.check(playerRadio);
        rangeFrom.setText(Integer.toString(rangeFromValue));
        rangeTo.setText(Integer.toString(rangeToValue));
        if(godMode) godModeRadio.setVisibility(View.VISIBLE);
}

    protected void saveSettings(boolean ifReset)
    {
        SharedPreferences.Editor preferencesEditor = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        if(ifReset)
        {
            preferencesEditor.putString(KEY_NAME, "Player");
            preferencesEditor.putInt(KEY_AGE, 0);
            preferencesEditor.putInt(KEY_RADIO, 1);
            preferencesEditor.putInt(KEY_RANGE_FROM, 1);
            preferencesEditor.putInt(KEY_RANGE_TO, 100);
            preferencesEditor.putInt(KEY_PLAYS, 0);
            preferencesEditor.putInt(KEY_WINS, 0);
            preferencesEditor.putInt(KEY_LOSE, 0);
            preferencesEditor.putInt(KEY_STREAK, 0);
            preferencesEditor.putInt(KEY_LONGEST, 0);
            preferencesEditor.putInt(KEY_TURNS, 7);
            preferencesEditor.putBoolean(KEY_LAST, false);
            preferencesEditor.putBoolean(KEY_FIRST, false);
            preferencesEditor.putBoolean(KEY_GODMODE, false);
            preferencesEditor.putInt(KEY_RADIO, 2130968587);
        }
        else
        {
            String playerName = nameField.getText().toString();
            int playerAge = Integer.parseInt(ageField.getText().toString());
            int radioID = radioGroup.getCheckedRadioButtonId();
            int rangeFromValue = Integer.parseInt(rangeFrom.getText().toString());
            int rangeToValue = Integer.parseInt(rangeTo.getText().toString());


            preferencesEditor.putString(KEY_NAME, playerName);
            preferencesEditor.putInt(KEY_AGE, playerAge);
            preferencesEditor.putInt(KEY_RADIO, radioID);
            preferencesEditor.putInt(KEY_RANGE_FROM, rangeFromValue);
            preferencesEditor.putInt(KEY_RANGE_TO, rangeToValue);
            preferencesEditor.putInt(KEY_TURNS, 7);
        }

        preferencesEditor.apply();
    }

    public void onButtonClick(View view)
    {
        if(view.getId() == R.id.saveSettingsBtn)
        {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        }
    }


    public void checkButton(View v)
    {
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        //Log.d("TAG", String.valueOf(radioButton.getId()));

    }


    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Settings")
                .setMessage("Are you sure you want to save changes?")

                .setPositiveButton("Yes, save my changes!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String eilute = "Changes saved successfully!";
                        if(resetStatistics.isChecked())
                        {
                            saveSettings(true);
                            eilute += " Statistics and achievements were reset.";
                        }
                        else
                            saveSettings(false);
                        Toast.makeText(getApplicationContext(), eilute, Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setNegativeButton("No, cancel changes!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Changes dismissed!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setNeutralButton("No, I still want to edit!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
