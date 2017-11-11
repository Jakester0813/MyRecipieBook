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
    private static String RECIPE_PREFS = "recipe_prefs";
    private static String TOTAL_CALORIES = "total_calories";
    private static String TODAY = "today";

    public static TotalCaloriesManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new TotalCaloriesManager(context);
        }
        return mInstance;
    }

    private TotalCaloriesManager(Context context){
        mPrefs = context.getSharedPreferences(RECIPE_PREFS,Context.MODE_PRIVATE);
    }

    public void addTotalCalories(int calories){
        if(checkDate()) {
            int totalCalories = mPrefs.getInt(TOTAL_CALORIES, 0);
            totalCalories += calories;
            mPrefs.edit().putInt(TOTAL_CALORIES, totalCalories).commit();
        }
        else{
            mPrefs.edit().putInt(TOTAL_CALORIES, calories).commit();
            setDate();
        }
    }

    public int getTotalCalories(){
        return mPrefs.getInt(TOTAL_CALORIES, 0);
    }

    public boolean checkDate(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day == mPrefs.getInt(TODAY,0);
    }

    public void setDate(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mPrefs.edit().putInt(TODAY,day).commit();
    }
}
