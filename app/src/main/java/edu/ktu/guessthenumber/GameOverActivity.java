package edu.ktu.guessthenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends Activity implements GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener{

    private static final String PREFERENCES_NAME = "PlayerInfo";
    private static final String KEY_PLAYS = "PlayerPlayCount";
    private static final String KEY_WINS = "PlayerWinCount";
    private static final String KEY_LOSE = "PlayerLostCount";
    private static final String KEY_STREAK = "CurrentWinStreak";
    private static final String KEY_LONGEST = "LongestWinStreak";
    private static final String KEY_FIRST = "FirstGuess";
    private static final String KEY_LAST = "LastGuess";
    private static final String KEY_TURNS = "MaxTurns";
    private static final String KEY_GODMODE = "GodMode";

    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int currentWinStreak;
    private int longestWinStreak;
    private int turnsLeft;
    private int maxTurns;
    GestureDetector gestureDetector;

    boolean hasWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        gestureDetector = new GestureDetector(GameOverActivity.this, GameOverActivity.this);


        loadStatistics();


        Bundle extrasBundle = getIntent().getExtras();
        hasWon = extrasBundle.getBoolean("HAS_WON", false);
        int randomNumber = extrasBundle.getInt("NUMBER", -1);
        turnsLeft = extrasBundle.getInt("TURNS", -1);

        TextView gameOverMessage = findViewById(R.id.GameOverText);
        ImageView gameOverImage = findViewById(R.id.GameOverImage);

        String messageString = "GAME OVER! \n";
        if(hasWon)
        {
            gamesPlayed += 1;
            gamesWon += 1;
            currentWinStreak += 1;
            messageString += "CONGRATULATIONS, YOU'VE WON!\n";
            gameOverMessage.setTextColor(getResources().getColor(R.color.camblue));
            gameOverImage.setImageDrawable(getResources().getDrawable(R.drawable.win));
        }
        else
        {
            gamesPlayed += 1;
            gamesLost += 1;
            currentWinStreak = 0;
            messageString += "YOU'VE RAN OUT OF TURNS! BETTER LUCK NEXT TIME!\n";
            gameOverMessage.setTextColor(getResources().getColor(R.color.yellow));
            gameOverImage.setImageDrawable(getResources().getDrawable(R.drawable.lose));
        }
        messageString+= "Random number was: " + Integer.toString(randomNumber);

        gameOverMessage.setText(messageString);
        if(currentWinStreak > longestWinStreak)
            longestWinStreak = currentWinStreak;

        saveStatistics();
    }


    protected void loadStatistics()
    {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        gamesPlayed = preferences.getInt(KEY_PLAYS, 0);
        gamesWon = preferences.getInt(KEY_WINS, 0);
        gamesLost = preferences.getInt(KEY_LOSE, 0);
        currentWinStreak = preferences.getInt(KEY_STREAK, 0);
        longestWinStreak = preferences.getInt(KEY_LONGEST, 0);
        maxTurns = preferences.getInt(KEY_TURNS, 0);
    }

    protected void saveStatistics()
    {
        SharedPreferences.Editor preferencesEditor = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();

        preferencesEditor.putInt(KEY_PLAYS, gamesPlayed);
        preferencesEditor.putInt(KEY_WINS, gamesWon);
        preferencesEditor.putInt(KEY_LOSE, gamesLost);
        preferencesEditor.putInt(KEY_STREAK, currentWinStreak);
        preferencesEditor.putInt(KEY_LONGEST, longestWinStreak);
        if(turnsLeft == 0 && hasWon)
            preferencesEditor.putBoolean(KEY_LAST, true);
        if(turnsLeft == (maxTurns - 1))
            preferencesEditor.putBoolean(KEY_FIRST, true);

        preferencesEditor.apply();
    }

    public void onButtonClick(View view)
    {
        if(view.getId() == R.id.playAgain)
        {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
    if(hasWon) {
        Toast.makeText(GameOverActivity.this, "Congratulations, You discovered God Mode!", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor preferencesEditor = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        preferencesEditor.putBoolean(KEY_GODMODE, true);
        preferencesEditor.apply();
        return true;
    }
    return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {

        return true;
    }




    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {

        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {

        return true;
    }
    @Override
    public void onShowPress(MotionEvent event) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent event) {

        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {

        return false;
    }
    @Override
    public void onLongPress(MotionEvent event) {

    }
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        return false;
    }
}
