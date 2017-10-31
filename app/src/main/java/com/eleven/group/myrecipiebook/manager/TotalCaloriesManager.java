package com.eleven.group.myrecipiebook.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jake on 10/31/2017.
 */

public class TotalCaloriesManager {
    private static TotalCaloriesManager mInstance;
    private SharedPreferences mPrefs;

    public static TotalCaloriesManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new TotalCaloriesManager(context);
        }
        return mInstance;
    }

    private TotalCaloriesManager(Context context){
        mPrefs = context.getSharedPreferences("recipe_prefs",Context.MODE_PRIVATE);
    }

    public void addTotalCalories(int calories){
        if(checkDate()) {
            int totalCalories = mPrefs.getInt("total_calories", 0);
            totalCalories += calories;
            mPrefs.edit().putInt("total_calories", totalCalories).commit();
        }
        else{
            mPrefs.edit().putInt("total_calories", calories).commit();
            setDate();
        }
    }

    public int getTotalCalories(){
        return mPrefs.getInt("total_calories", 0);
    }

    public boolean checkDate(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day == mPrefs.getInt("today",0);
    }

    public void setDate(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mPrefs.edit().putInt("today",day).commit();
    }
}
