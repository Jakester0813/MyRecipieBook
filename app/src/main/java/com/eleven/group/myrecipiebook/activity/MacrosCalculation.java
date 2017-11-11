package com.eleven.group.myrecipiebook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.interfaces.SearchRecipeInterface;
import com.eleven.group.myrecipiebook.interfaces.SearchRecipesInterface;
import com.eleven.group.myrecipiebook.manager.TotalCaloriesManager;
import com.eleven.group.myrecipiebook.model.Nutrition;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.eleven.group.myrecipiebook.model.RecipeResponse;
import com.eleven.group.myrecipiebook.model.RecipesResponse;
import com.eleven.group.myrecipiebook.network.YummlyClient;
import com.eleven.group.myrecipiebook.util.RecipeUtility;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.description;
import static android.util.Log.d;

public class MacrosCalculation extends AppCompatActivity {

    AsyncHttpClient client;
    Toolbar mToolbar;
    private float[] yData = new float[3];
    private String[] xData = {"Protein", "Carbohydrates", "Fat"};

    ProgressDialog mDialog;
    LinearLayout mMainLinear;

    TextView macroCalories, mCaloriesFromText, mTotalCalories, mProteinMacro, mCarbMacro, mFatMacro;
    PieChart pieChart;
    String recipeQuery, recipeString;
    RecipeResponse recipe;

    SearchRecipesInterface mRecipesInterface;
    SearchRecipeInterface mRecipeInterface;

    double totalCalories = 0.0;
    double proteinCalories = 0.0;
    double carboCalories = 0.0;
    double fatCalories = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macros_calculation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        recipeQuery = getIntent().getStringExtra("recipeQuery");
        recipeString = recipeQuery.substring(0,1).toUpperCase() + recipeQuery.substring(1);
        mToolbar.setTitle("Macronutrients from " + recipeString);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.text_icon_color));
        setSupportActionBar(mToolbar);
        mDialog = new ProgressDialog(this);
        mMainLinear = (LinearLayout) findViewById(R.id.ll_main);
        mDialog.setMessage("Loading Macro Data. Please wait...");
        mDialog.show();
        mCaloriesFromText = (TextView) findViewById(R.id.tvCalories);
        mTotalCalories = (TextView) findViewById(R.id.tvMacroCaloriesTotal);
        mProteinMacro = (TextView) findViewById(R.id.tvMacroProtein);
        mCarbMacro = (TextView) findViewById(R.id.tvMacroCarbs);
        mFatMacro = (TextView) findViewById(R.id.tvMacroFat);

        try {
            handleFoodQuery(recipeQuery);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        macroCalories = (TextView) findViewById(R.id.tvMacroCalories);
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(48f);
        pieChart.setTransparentCircleAlpha(45);



        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                d("onValueSelected: ", e.toString());
                d("onValueSelected: ", h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String percent = e.toString().substring(pos1+3);
                for(int i=0;i<yData.length;i++){
                    if(yData[i] == Float.parseFloat(percent)){
                        pos1 = i;
                        break;
                    }
                }
                String macro = xData[pos1];
                Toast.makeText(MacrosCalculation.this, "Macros " +macro+ "\n" + "Macro %:" +percent,
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void addDataSet(){
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i=0;i<yData.length;i++){
            yEntrys.add(new PieEntry(yData[i], i));
        }
        for(int j=0;j<xData.length;j++){
            xEntrys.add(xData[j]);
        }

        // create data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Macro %");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(16);

        // add colors to data set
        int[] colors = {getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimaryLight)};
        pieDataSet.setColors(colors);

        pieDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return Double.toString((double)Math.round(value * 10d)/10d) + "%";
            }
        });

        // add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(16f);

        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int k = 0; k < xData.length; k++){
            LegendEntry l = new LegendEntry();
            l.formColor = colors[k];
            l.label = xData[k];
            entries.add(l);
        }
        legend.setCustom(entries);
        legend.setXEntrySpace(22f);


        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        mDialog.dismiss();
        mMainLinear.setVisibility(View.VISIBLE);
        pieChart.invalidate();
        pieChart.animateXY(1000,1000);
    }

    public void calorieCalculation(){
        ArrayList<Nutrition> nutritions = recipe.getNutrition();

        for(Nutrition nutrition: nutritions){
            if(nutrition.getAttribute().equals("PROCNT")){
                proteinCalories = 4*nutrition.getValue();
                mProteinMacro.setText(Double.toString(nutrition.getValue())+"g");
                totalCalories = totalCalories+proteinCalories;
            }
            else if(nutrition.getAttribute().equals("CHOCDF")){
                carboCalories = 4*nutrition.getValue();
                mCarbMacro.setText(Double.toString(nutrition.getValue())+"g");
                totalCalories = totalCalories+carboCalories;
            }
            else if(nutrition.getAttribute().equals("FAT")){
                fatCalories = 9*nutrition.getValue();
                mFatMacro.setText(Double.toString(nutrition.getValue())+"g");
                totalCalories = totalCalories+fatCalories;
            }
        }

        yData[0] = (float)((proteinCalories/totalCalories)*100);
        yData[1] = (float)((carboCalories/totalCalories)*100);
        yData[2] = (float)((fatCalories/totalCalories)*100);
        macroCalories.setText(Integer.toString((int)totalCalories));
        TotalCaloriesManager.getInstance(this).addTotalCalories((int)totalCalories);
        mTotalCalories.setText(Integer.toString(TotalCaloriesManager.getInstance(this).getTotalCalories()));
        if(Integer.parseInt(mTotalCalories.getText().toString()) >= 2000){
            mTotalCalories.setTextColor(Color.RED);
        }
        else{
            mTotalCalories.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
        }

        addDataSet();
    }

    //Calls to get the recipes based on the query
    public void handleFoodQuery(String query) throws UnsupportedEncodingException {

        RecipeUtility.getRecipesService().getRecipes(getString(R.string.YUMMLY_APP_ID),
                getString(R.string.YUMMLY_API_KEY),query).enqueue(new Callback<RecipesResponse>() {
            @Override
            public void onResponse(Call<RecipesResponse> call, Response<RecipesResponse> response) {
                try {
                    getRecipeForNutrition(response.body().getRecipes().get(0).getId());
                } catch (UnsupportedEncodingException e) {
                    mDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RecipesResponse> call, Throwable t) {
                mDialog.dismiss();
            }
        });

    }

    public void getRecipeForNutrition(String id) throws UnsupportedEncodingException {

        RecipeUtility.getRecipeService().getRecipe(id, getString(R.string.YUMMLY_APP_ID),
                getString(R.string.YUMMLY_API_KEY)).enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                recipe = response.body();
                calorieCalculation();
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

}
