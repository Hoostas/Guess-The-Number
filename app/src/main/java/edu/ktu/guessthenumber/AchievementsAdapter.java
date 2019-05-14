package edu.ktu.guessthenumber;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

public class AchievementsAdapter extends BaseAdapter {

    List<AchievementData> achievementData;
    Context context;

    AchievementsAdapter(Context inContext, List<AchievementData> inData)
    {
        context = inContext;
        achievementData = inData;
    }

    public int getCount()
    {
        if(achievementData != null)
        {
            return achievementData.size();
        }
        return 0;
    }
    public int getEarnedCount()
    {
        int counter = 0;
        if(achievementData != null)
        {
            for(int i = 0; i < achievementData.size(); i++)
            {
                if(achievementData.get(i).isEarned)
                {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public Object getItem(int id)
    {
        if(achievementData != null)
        {
            if(id >= 0 && id < achievementData.size())
            {
                return achievementData.get(id);
            }
        }
        return null;
    }

    public long getItemId(int id)
    {
        if(achievementData != null)
        {
            if(id >= 0 && id < achievementData.size())
            {
                return id;
            }
        }
        return -1;
    }


    @Override
    public View getView(int id, View view, ViewGroup viewGroup)
    {
        View result = view;

        if(view == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            result = layoutInflater.inflate(R.layout.achievement_list_item, null);
        }

        AchievementData data = (AchievementData) getItem(id);
        if(data != null)
        {
            ImageView image = result.findViewById(R.id.list_image);
            TextView name = result.findViewById(R.id.list_name);
            TextView requirements = result.findViewById(R.id.list_requirements);

            int drawableId = R.drawable.error;

            try {
                Class res = R.drawable.class;
                Field field = res.getField(data.image);
                drawableId = field.getInt(null);
            }
            catch (Exception e) {
                Log.e("MyTag", "Failure to get drawable id.", e);
            }


            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

            if(!data.isEarned)
                image.setColorFilter(filter);
            image.setImageResource(drawableId);
            name.setText(data.name);
            requirements.setText(data.requirements);
        }
        return result;
    }
}
