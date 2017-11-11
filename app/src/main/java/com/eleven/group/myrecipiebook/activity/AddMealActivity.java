package com.eleven.group.myrecipiebook.activity;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.eleven.group.myrecipiebook.model.SearchRecipe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static android.util.Log.d;



public class AddMealActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    TextView mResult;
    AsyncHttpClient client;
    Button mYesBtn, mNoBtn;
    String food;

    /*
     * Created By: Jake Rushing
     */

    private static String RECIPE_QUERY = "recipeQuery";
    private static String YOU_SAID = "You said you had a \'";
    private static String IS_IT_CORRECT = "\'. Is this correct?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        promptSpeechInput();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        mResult = (TextView)findViewById(R.id.tv_result);
        mYesBtn = (Button) findViewById(R.id.btn_yes);
        mNoBtn = (Button) findViewById(R.id.btn_no);
        client = new AsyncHttpClient();
        mYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddMealActivity.this, MacrosCalculation.class);
                i.putExtra(RECIPE_QUERY, food);
                startActivity(i);
            }
        });
        mNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResult.setText("");
                mYesBtn.setVisibility(View.GONE);
                mNoBtn.setVisibility(View.GONE);
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    mYesBtn.setVisibility(View.VISIBLE);
                    mNoBtn.setVisibility(View.VISIBLE);
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    food = result.get(0);
                    StringBuilder sb = new StringBuilder(YOU_SAID);
                    sb.append(result.get(0)).append(IS_IT_CORRECT);
                    mResult.setText(sb.toString());
                }
                break;
            }
        }
    }
}
