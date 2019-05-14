package edu.ktu.guessthenumber;

public class AchievementsEntry {

    private int mID;
    private String mIcon;
    private String mName;
    private String mRequirements;
    private String mIsEarned;

    public AchievementsEntry()
    {
        mID = 0;
        mIcon = "";
        mName = "";
        mRequirements = "";
        mIsEarned = "false";
    }

    public AchievementsEntry(int id, String icon, String name, String requirements, String isEarned)
    {
        mID = id;
        mIcon = icon;
        mName = name;
        mRequirements = requirements;
        mIsEarned = isEarned;
    }

    public void setID(int val)
    {
        mID = val;
    }
    public int getID()
    {
        return mID;
    }

    public void setIcon(String val)
    {
        mIcon = val;
    }
    public String getIcon()
    {
        return mIcon;
    }

    public void setName(String val)
    {
        mName = val;
    }
    public String getName()
    {
        return mName;
    }

    public void setRequirements(String val)
    {
        mRequirements = val;
    }
    public String getRequirements()
    {
        return mRequirements;
    }

    public void setIsEarned(String val)
    {
        mIsEarned = val;
    }
    public String getIsEarned()
    {
        return mIsEarned;
    }

}
