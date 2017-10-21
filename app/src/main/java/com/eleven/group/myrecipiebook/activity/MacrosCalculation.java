package com.eleven.group.myrecipiebook.activity;

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

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.description;

public class MacrosCalculation extends AppCompatActivity {

    private float[] yData = {40.0f, 40.0f, 20.0f};
    private String[] xData = {"Proteins", "Carbohydrates", "Fats"};

    TextView date;
    TextView macroCalories;
    PieChart pieChart;
    Recipe recipe;

    //double calories = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macros_calculation);
        recipe = Parcels.unwrap(getIntent().getParcelableExtra("macroRecipe"));


        date = (TextView) findViewById(R.id.tvDate);
        macroCalories = (TextView) findViewById(R.id.tvMacroCalories);
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        //pieChart.setDescription("Macros (In calories)");
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);

        addDataSet();
        //calorieCalculation();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("onValueSelected: ", e.toString());
                Log.d("onValueSelected: ", h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String calories = e.toString().substring(pos1+3);

                for(int i=0;i<yData.length;i++){
                    if(yData[i] == Float.parseFloat(calories)){
                        pos1 = i;
                        break;
                    }
                }

                String macros = xData[pos1];
                Toast.makeText(MacrosCalculation.this, "Macros " +macros+ "\n" + "Calories:" +calories,
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
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Calories");
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

    /*public void calorieCalculation(){
        ArrayList<Nutrition> nutritions = recipe.getNutritionEstimates();

        for(int j=0;j<nutritions.size();j++){
            if(nutritions.get(j).getAttribute().equals("PROCNT")){
                calories = calories+(4*nutritions.get(j).getValue());
            }
            else if(nutritions.get(j).getAttribute().equals("CHOCDF")){
                calories = calories+(4*nutritions.get(j).getValue());
            }
            else if(nutritions.get(j).getAttribute().equals("FAT")){
                calories = calories+(9*nutritions.get(j).getValue());
            }
        }

        macroCalories.setText("" +Double.toString(calories)+ "/2000");
    }*/

}
