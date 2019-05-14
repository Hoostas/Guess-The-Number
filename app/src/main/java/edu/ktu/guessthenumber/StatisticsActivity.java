package edu.ktu.guessthenumber;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatisticsActivity extends Activity {

    private static final String PREFERENCES_NAME = "PlayerInfo";
    private static final String KEY_PLAYS = "PlayerPlayCount";
    private static final String KEY_WINS = "PlayerWinCount";
    private static final String KEY_LOSE = "PlayerLostCount";
    private static final String KEY_NAME = "PlayerName";
    private static final String KEY_STREAK = "CurrentWinStreak";
    private static final String KEY_LONGEST = "LongestWinStreak";


    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int currentWinStreak;
    private int longestWinStreak;
    private String playerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        loadStatistics();
        initializeSimpleAdapter();
        addToSimpleAdapter("Player:", playerName);
        addToSimpleAdapter("Total games played:", String.valueOf(gamesPlayed));
        addToSimpleAdapter("Games won:", String.valueOf(gamesWon));
        addToSimpleAdapter("Games lost:", String.valueOf(gamesLost));
        int percentage = (int) (((double) gamesWon / (double) gamesPlayed) * 100);
        addToSimpleAdapter("Win / loss ratio:", String.valueOf(percentage)+"%");
        addToSimpleAdapter("Current win streak:", String.valueOf(currentWinStreak));
        addToSimpleAdapter("Longest win streak:", String.valueOf(longestWinStreak));
    }


    SimpleAdapter simpleAdapter;
    List<Map<String, String>> simpleData;

    void initializeSimpleAdapter()
    {
        simpleData = new ArrayList<>();
        String[] from = {"NAME", "VALUE"};

        int[] to = {R.id.list_stat, R.id.list_value};
        simpleAdapter = new SimpleAdapter(this, simpleData, R.layout.statistics_list_item, from, to);

        ListView listView = findViewById(R.id.statsList);
        listView.setAdapter(simpleAdapter);
    }

    void addToSimpleAdapter(String name, String value)
    {
        Map<String, String> data = new HashMap<>();
        data.put("NAME", name);
        data.put("VALUE", value);
        simpleData.add(data);
        simpleAdapter.notifyDataSetChanged();
    }


    protected void loadStatistics()
    {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        gamesPlayed = preferences.getInt(KEY_PLAYS, 0);
        gamesWon = preferences.getInt(KEY_WINS, 0);
        gamesLost = preferences.getInt(KEY_LOSE, 0);
        playerName = preferences.getString(KEY_NAME, "Player");
        currentWinStreak = preferences.getInt(KEY_STREAK, 0);
        longestWinStreak = preferences.getInt(KEY_LONGEST, 0);
    }
}
