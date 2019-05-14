package edu.ktu.guessthenumber;

public class AchievementData {

    public String image;
    public String name;
    public String requirements;
    public boolean isEarned;

    public AchievementData(){
        image = "";
        name = "";
        requirements = "";
        isEarned = true;
    }
    public AchievementData(String inImage, String inName, String inRequirements, boolean inIsEarned){
        image = inImage;
        name = inName;
        requirements = inRequirements;
        isEarned = inIsEarned;
    }
}
