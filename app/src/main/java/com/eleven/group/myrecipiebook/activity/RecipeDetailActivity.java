package com.eleven.group.myrecipiebook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RecipeDetailActivity extends AppCompatActivity {

    ImageView ivRecipeImage;
    TextView tvTitle;
    TextView tvPublisher;
    TextView tvSocialRank;
    ListView lvIngredients;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        getRecipeFood2Fork(recipe);

    }

    public void detailView(Recipe recipe){
        ivRecipeImage = (ImageView) findViewById(R.id.ivRecipeImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        tvSocialRank = (TextView) findViewById(R.id.tvSocialRank);
        lvIngredients = (ListView) findViewById(R.id.lvIngredients);

        String image = recipe.getImageUrl();
        Glide.with(getApplicationContext()).load(image).into(ivRecipeImage);
        tvTitle.setText(recipe.getTitle());
        tvPublisher.setText(recipe.getPublisher());
        tvSocialRank.setText(""+recipe.getSocialRank());

        // Create the adapter to convert the array to views
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipe.getIngredients());
        // Attach the adapter to a ListView
        lvIngredients.setAdapter(adapter);
    }

    public void getRecipeFood2Fork(Recipe recipe) {
        AsyncHttpClient client = new AsyncHttpClient();
        String strUrl = getString(R.string.FOOD2FORK_RECIPE_API);
        String apiKey = getString(R.string.FOOD2FORK_API_KEY);
        long recipeId = recipe.getRecipeId();

        RequestParams params = new RequestParams();
        params.put("key", apiKey);
        params.put("rId", recipeId);

        client.get(strUrl, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG",response.toString());

                JSONObject recipeJsonResult = null;
                try{
                    recipeJsonResult = response.getJSONObject("recipe");
                    Log.d("getRecipeFood2Fork:", recipeJsonResult.toString());
                    detailView(Recipe.fromJSONObject(recipeJsonResult));
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject json) {
                Log.d("onFailure: ", "" + statusCode);
                Log.d("onFailure: ", "" + json);
            }
        });
    }

}
