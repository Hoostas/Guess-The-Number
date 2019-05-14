package edu.ktu.guessthenumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AchievementsDatabaseHandler extends SQLiteOpenHelper {

    Context nContext;
    private static final String DATABASE_NAME = "Achievements";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RESULTS = "Results";
    private static final String KEY_ID = "id";
    private static final String KEY_ICON = "icon";
    private static final String KEY_NAME = "name";
    private static final String KEY_REQUIREMENTS = "requirements";
    private static final String KEY_ISEARNED = "isEarned";

    public AchievementsDatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_RESULTS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_ICON + " TEXT, " +
                KEY_NAME + " TEXT, " +
                KEY_REQUIREMENTS + " TEXT, " +
                KEY_ISEARNED + " TEXT " +
                ")";

        db.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS " + TABLE_RESULTS;
        db.execSQL(query);
        onCreate(db);
    }

    public long addEntry(AchievementsEntry entry)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_ICON, entry.getIcon());
        cv.put(KEY_NAME, entry.getName());
        cv.put(KEY_REQUIREMENTS, entry.getRequirements());
        cv.put(KEY_ISEARNED, entry.getIsEarned());

        long id = db.insert(TABLE_RESULTS, null, cv);
        db.close();
        return id;
    }

    public AchievementsEntry getEntry(int id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        cursor = db.query(TABLE_RESULTS, new String[] { KEY_ID, KEY_ICON, KEY_NAME, KEY_REQUIREMENTS, KEY_ISEARNED}, KEY_ID + "=?", new String[] { Integer.toString(id) }, null, null, null);

        AchievementsEntry entry = new AchievementsEntry();
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                entry.setID(cursor.getInt(0));
                entry.setIcon(cursor.getString(1));
                entry.setName(cursor.getString(2));
                entry.setRequirements(cursor.getString(3));
                entry.setIsEarned(cursor.getString(4));
            }
        }
        cursor.close();
        db.close();

        return entry;
    }

    public ArrayList<AchievementsEntry> getAllEntries()
    {
        ArrayList<AchievementsEntry> entries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_RESULTS + " ORDER BY " + KEY_ISEARNED + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    AchievementsEntry entry = new AchievementsEntry();
                    entry.setID(cursor.getInt(0));
                    entry.setIcon(cursor.getString(1));
                    entry.setName(cursor.getString(2));
                    entry.setRequirements(cursor.getString(3));
                    entry.setIsEarned(cursor.getString(4));
                    entries.add(entry);
                } while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return entries;
    }

    public void deleteEntry(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_RESULTS, KEY_ID + "=?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void updateEntry(AchievementsEntry entry)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_ICON, entry.getIcon());
        cv.put(KEY_NAME, entry.getName());
        cv.put(KEY_REQUIREMENTS, entry.getRequirements());
        cv.put(KEY_ISEARNED, entry.getIsEarned());

        db.update(TABLE_RESULTS, cv, KEY_ID + "=?", new String[] { Integer.toString(entry.getID()) });

        db.close();
    }

}
