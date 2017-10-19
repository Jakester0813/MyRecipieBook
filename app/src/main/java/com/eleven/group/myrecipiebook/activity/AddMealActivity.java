package com.eleven.group.myrecipiebook.activity;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AddMealActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    TextView mResult;

    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        promptSpeechInput();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        mResult = (TextView)findViewById(R.id.tv_result);
        client = new AsyncHttpClient();
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
                Log.d("DEBUG",response.toString());

                JSONObject recipeJsonResult = null;
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
                Log.d("onFailure: ", "" + throwable.toString());
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
                Recipe recipe = Recipe.fromJSONObject(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject json) {
                Log.d("onFailure: ", "" + statusCode);
                Log.d("onFailure: ", "" + json);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("onSuccess: ", "" + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("onFailure: ", "" + errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("onFailure: ", "" + throwable.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("onSuccess: ", "" + responseString);
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

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mResult.setText(result.get(0));
                    try {
                        handleFoodQuery(result.get(0));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

        }
    }
}
