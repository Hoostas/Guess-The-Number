package edu.ktu.guessthenumber;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementsActivity extends Activity {

    private ProgressBar progressBar;
    private ObjectAnimator progressAnimator;
    AchievementsDatabaseHandler dbHandler = new AchievementsDatabaseHandler(this);

    private static final String PREFERENCES_NAME = "PlayerInfo";
    private static final String KEY_PLAYS = "PlayerPlayCount";
    private static final String KEY_WINS = "PlayerWinCount";
    private static final String KEY_LOSE = "PlayerLostCount";
    private static final String KEY_FIRST = "FirstGuess";
    private static final String KEY_LAST = "LastGuess";
    private static final String KEY_LONGEST = "LongestWinStreak";
    private static final String KEY_GODMODE = "GodMode";
    private static final String TABLE_RESULTS = "Results";

    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private boolean first;
    private boolean last;
    private int longest;
    private  boolean godmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_achievements);
        TextView earned = findViewById(R.id.textEarned);
        TextView progress = findViewById(R.id.textProgress);
        loadStatistics();
        initializeCustomAdapter();
        dbHandler.onUpgrade(dbHandler.getWritableDatabase(), 1, 1);
        createAchievements();
        updateAchievements();

        for (AchievementsEntry allEntry : dbHandler.getAllEntries()) {
            boolean isEarned = false;
            if(allEntry.getIsEarned().equals("true")) isEarned = true;
            addToCustomAdapter(allEntry.getIcon(), allEntry.getName(), allEntry.getRequirements(), isEarned);
        }

        AchievementsEntry entry;
        if(customAdapter.getEarnedCount() >= customAdapter.getCount() - 1)
        {
            int id = customAdapter.getCount();
            customAdapterData.remove(id - 1);

            entry = dbHandler.getEntry(id);
            entry.setIsEarned("true");
            dbHandler.updateEntry(entry);
            addToCustomAdapter(entry.getIcon(), entry.getName(), entry.getRequirements(), true);

        }
       // Log.d("TAG", String.valueOf(customAdapter.getEarnedCount()) + "   " + String.valueOf(customAdapter.getCount()-1));

        int percentage = (int) (((double) customAdapter.getEarnedCount() / (double) customAdapter.getCount()) * 100);
        progressBar = findViewById(R.id.progressBar4);
        progressAnimator = ObjectAnimator.ofInt(progressBar, "progress",0, percentage);
        progressAnimator.setDuration(1000);
        progressAnimator.start();
        earned.setText("Earned: " + String.valueOf(customAdapter.getEarnedCount()) + "/" + String.valueOf(customAdapter.getCount()));
        progress.setText("Progress: " + percentage + "%");



    }


    AchievementsAdapter customAdapter;
    List<AchievementData> customAdapterData;

    void initializeCustomAdapter()
    {
        customAdapterData = new ArrayList<>();
        customAdapter = new AchievementsAdapter(this, customAdapterData);

        ListView listView = findViewById(R.id.achievementsList);
        listView.setAdapter(customAdapter);
    }


    void addToCustomAdapter(String image, String name, String requirements, boolean isEarned)
    {
        customAdapterData.add(new AchievementData(image, name, requirements, isEarned));
        customAdapter.notifyDataSetChanged();
    }

    protected void loadStatistics()
    {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        gamesPlayed = preferences.getInt(KEY_PLAYS, 0);
        gamesWon =  preferences.getInt(KEY_WINS, 0);
        gamesLost = preferences.getInt(KEY_LOSE, 0);
        first = preferences.getBoolean(KEY_FIRST, false);
        last = preferences.getBoolean(KEY_LAST, false);
        longest = preferences.getInt(KEY_LONGEST, 0);
        godmode = preferences.getBoolean(KEY_GODMODE, false);

    }

    void updateAchievements()
    {
        String earned = "true";
        AchievementsEntry entry;

        if(gamesPlayed >= 1) {entry = dbHandler.getEntry(1); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesWon >= 1) {entry = dbHandler.getEntry(2); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(first) {entry = dbHandler.getEntry(3); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(last) {entry = dbHandler.getEntry(4); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(longest >= 10) {entry = dbHandler.getEntry(5); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(longest == 100) {entry = dbHandler.getEntry(6); entry.setIsEarned(earned); dbHandler.updateEntry(entry);} //TODO
        if(gamesWon >= 25) {entry = dbHandler.getEntry(7); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesWon >= 50) {entry = dbHandler.getEntry(8); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesWon >= 100) {entry = dbHandler.getEntry(9); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesWon >= 500) {entry = dbHandler.getEntry(10); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesWon >= 1000) {entry = dbHandler.getEntry(11); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesPlayed >= 10) {entry = dbHandler.getEntry(12); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesPlayed >= 100) {entry = dbHandler.getEntry(13); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesPlayed >= 250) {entry = dbHandler.getEntry(14); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesPlayed >= 500) {entry = dbHandler.getEntry(15); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesPlayed >= 1000) {entry = dbHandler.getEntry(16); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesPlayed >= 5000) {entry = dbHandler.getEntry(17); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(gamesLost >= 1) {entry = dbHandler.getEntry(18); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}
        if(godmode) {entry = dbHandler.getEntry(19); entry.setIsEarned(earned); dbHandler.updateEntry(entry);}


    }
    void createAchievements()
    {
        String notEarned = "false";
        dbHandler.addEntry(new AchievementsEntry(1,"achievement1", "Feels Like The First Time", "Play your very first game", notEarned ));
        dbHandler.addEntry(new AchievementsEntry(2,"achievement25","I'm Done Learning", "Win your very first game", notEarned));
        dbHandler.addEntry(new AchievementsEntry(3,"achievement10","The Luck of the Irish", "Win a game at first guess", notEarned));
        dbHandler.addEntry(new AchievementsEntry(4,"achievement24","Well That Was Easy", "Win a game at the last try", notEarned));
        dbHandler.addEntry(new AchievementsEntry(5,"achievement2","Chainlinked", "Win 10 games in a row", notEarned));
        dbHandler.addEntry(new AchievementsEntry(6,"achievement3","Tinkerer", "Visit 'Settings' section", notEarned));
        dbHandler.addEntry(new AchievementsEntry(7,"achievement11","Just Wait a Moment", "Win 25 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(8,"achievement12","Warming Up", "Win 50 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(9,"achievement13","Always Improving", "Win 100 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(10,"achievement14","Better Than You Were", "Win 500 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(11,"achievement26","Capped Out... For Now", "Win 1000 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(12,"achievement4","Newbie", "Play 10 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(13,"achievement5","Novice", "Play 100 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(14,"achievement6","Expert", "Play 250 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(15,"achievement7","Hardcore", "Play 500 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(16,"achievement8","Champion", "Play 1000 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(17,"achievement9","Obsessed", "Play 5000 games", notEarned));
        dbHandler.addEntry(new AchievementsEntry(18,"achievement15","It happens to the best of us", "Lose one game", notEarned));
        if(godmode)
            dbHandler.addEntry(new AchievementsEntry(19,"achievement19","Unleash the Power", "Unlock the God Mode", "true"));
        else
            dbHandler.addEntry(new AchievementsEntry(19,"achievement192","???", "Hidden Achievement", notEarned));
        dbHandler.addEntry(new AchievementsEntry(20,"achievement46","Completionist", "Unlock all achievements", notEarned));
    }

}
