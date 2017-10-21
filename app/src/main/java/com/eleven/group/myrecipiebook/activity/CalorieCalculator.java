package com.eleven.group.myrecipiebook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eleven.group.myrecipiebook.R;

public class CalorieCalculator extends AppCompatActivity {

    Toolbar toolbar;
    double menBMR;
    double womenBMR;
    int age;

    RadioGroup radioGroup;
    RadioButton radioGenderButton;
    NumberPicker ageNumber;
    EditText weight;
    EditText height;
    TextView calories;
    Button btnCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupView();
    }

    public void setupView(){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ageNumber = (NumberPicker) findViewById(R.id.npAge);
        weight = (EditText) findViewById(R.id.etWeight);
        height = (EditText) findViewById(R.id.etHeight);
        calories = (TextView) findViewById(R.id.tvCalories);
        btnCal = (Button) findViewById(R.id.btnCalculate);

        ageNumber.setMinValue(18);
        ageNumber.setMaxValue(60);
        ageNumber.setWrapSelectorWheel(true);

        ageNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                age = newVal;  //Display the newly selected number from picker
            }
        });

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("btn clicked", "calorie");
                int selectedId = radioGroup.getCheckedRadioButtonId(); // get selected radio button
                radioGenderButton = (RadioButton) findViewById(selectedId); // find radiobutton
                Log.d("radioGenderButton: ", radioGenderButton.getText().toString());

                if(radioGenderButton.getText().toString().equals("Male")){
                    menBMR = (10*Integer.parseInt(weight.getText().toString())) +
                            (6.25*Integer.parseInt(height.getText().toString())) - (5*age) + 5;
                    calories.setText(Double.toString(menBMR));
                }
                else if(radioGenderButton.getText().toString().equals("Female")){
                    womenBMR = (10*Integer.parseInt(weight.getText().toString())) + (6.25*Integer.parseInt(height.getText().toString())) - (5*age) - 161;
                    calories.setText(Double.toString(womenBMR));
                }
            }
        });
    }

}
