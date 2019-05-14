package edu.ktu.guessthenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartGameClick()
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
    public void onSettingsClick()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void onAchievementsClick()
    {
        Intent intent = new Intent(this, AchievementsActivity.class);
        startActivity(intent);
    }
    public void onAboutClick()
    {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    public void onStatisticsClick()
    {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }


    public void onButtonClick(View view)
    {
        if(view.getId() == R.id.startGameBtn)
        {
            onStartGameClick();
        }
        else if (view.getId() == R.id.statisticsBtn)
        {
            onStatisticsClick();
        }
        else if(view.getId() == R.id.achievementsBtn)
        {
            onAchievementsClick();
        }
        else if (view.getId() == R.id.settingBtn)
        {
            onSettingsClick();
        }
        else if(view.getId() == R.id.aboutBtn)
        {
            onAboutClick();
        }
        else if(view.getId() == R.id.exitBtn)
        {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        }
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Quit")
                .setMessage("Are you sure you want to quit the game?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .create();
        return myQuittingDialogBox;

    }
}
