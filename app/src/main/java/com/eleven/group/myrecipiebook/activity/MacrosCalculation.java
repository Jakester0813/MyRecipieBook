package com.eleven.group.myrecipiebook.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class MacrosCalculation extends AppCompatActivity {

    private float[] yData = {20.0f, 30.0f, 50.0f};
    private String[] xData = {"Proteins", "Carbohydrates", "Fats"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macros_calculation);

        pieChart = (PieChart) findViewById(R.id.idPieChart);
        //pieChart.setDescription("Macros (In calories)");
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);

        addDataSet();

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

                String macros = xData[pos1+1];
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
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
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

}
