package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.model.Nutrition;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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

import static android.R.attr.description;
import static android.util.Log.d;

public class MacrosCalculation extends AppCompatActivity {

    AsyncHttpClient client;
    //private float[] yData = {40.0f, 40.0f, 20.0f};
    private float[] yData = new float[3];
    private String[] xData = {"Proteins", "Carbohydrates", "Fats"};

    TextView macroCalories;
    PieChart pieChart;
    String recipeQuery;
    Recipe recipe;

    double totalCalories = 0.0;
    double proteinCalories = 0.0;
    double carboCalories = 0.0;
    double fatCalories = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macros_calculation);
        recipeQuery = getIntent().getStringExtra("recipeQuery");

        try {
            handleFoodQuery(recipeQuery);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        macroCalories = (TextView) findViewById(R.id.tvMacroCalories);
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        //pieChart.setDescription("Macros (In calories)");
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);

        calorieCalculation();

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
        pieDataSet.setValueTextSize(12);

        // add colors to data set
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);

        // add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    public void calorieCalculation(){
        ArrayList<Nutrition> nutritions = recipe.getNutritionEstimates();

        for(Nutrition nutrition: nutritions){
            if(nutrition.getAttribute().equals("PROCNT")){
                proteinCalories = 4*nutrition.getValue();
                totalCalories = totalCalories+proteinCalories;
            }
            else if(nutrition.getAttribute().equals("CHOCDF")){
                carboCalories = 4*nutrition.getValue();
                totalCalories = totalCalories+carboCalories;
            }
            else if(nutrition.getAttribute().equals("FAT")){
                fatCalories = 9*nutrition.getValue();
                totalCalories = totalCalories+fatCalories;
            }
        }

        yData[0] = (float)((proteinCalories/totalCalories)*100);
        yData[1] = (float)((carboCalories/totalCalories)*100);
        yData[2] = (float)((fatCalories/totalCalories)*100);
        macroCalories.setText("" +Double.toString(totalCalories)+ "/2000");

        addDataSet();
    }

    //Calls to get the recipes based on the query
    public void handleFoodQuery(String query) throws UnsupportedEncodingException {
        String strUrl = getString(R.string.YUMMLY_SEARCH_RECIPE_API);
        String apiId = getString(R.string.YUMMLY_APP_ID);
        String apiKey = getString(R.string.YUMMLY_API_KEY);

        RequestParams params = new RequestParams();
        params.put("_app_id", apiId);
        params.put("_app_key", apiKey);
        params.put("q", query);

        client.get(strUrl, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                d("DEBUG",response.toString());

                try{
                    //then retrieves the first result and makes the call using the first recipe's recipe id
                    getRecipeForNutrition(response.getJSONArray("matches").getJSONObject(1).getString("id"));
                }
                catch(JSONException e){
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                d("onFailure: ", "" + throwable.toString());
            }
        });
    }

    public void getRecipeForNutrition(String id) throws UnsupportedEncodingException {
        StringBuilder strUrl = new StringBuilder(getString(R.string.YUMMLY_GET_RECIPE_API));
        strUrl.append("/").append(id);
        String apiId = getString(R.string.YUMMLY_APP_ID);
        String apiKey = getString(R.string.YUMMLY_API_KEY);

        RequestParams params = new RequestParams();
        params.put("_app_id", apiId);
        params.put("_app_key", apiKey);
        client.get(strUrl.toString(), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                d("getNutritionResponse:",response.toString());
                recipe = Recipe.fromJSONObject(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject json) {
                d("onFailure: ", "" + statusCode);
                d("onFailure: ", "" + json);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                d("onSuccess: ", "" + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                d("onFailure: ", "" + errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                d("onFailure: ", "" + throwable.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                d("onSuccess: ", "" + responseString);
            }
        });
    }

}
