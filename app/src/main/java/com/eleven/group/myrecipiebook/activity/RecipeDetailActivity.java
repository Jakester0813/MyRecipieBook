package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.eleven.group.myrecipiebook.model.RecipeResponse;
import com.eleven.group.myrecipiebook.model.SearchRecipe;
import com.eleven.group.myrecipiebook.network.YummlyClient;
import com.eleven.group.myrecipiebook.util.RecipeUtility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.apiKey;
import static com.eleven.group.myrecipiebook.R.id.ivRecipeImage;
import static com.eleven.group.myrecipiebook.R.id.lvIngredients;
import static com.eleven.group.myrecipiebook.R.id.tvRating;
import static com.eleven.group.myrecipiebook.R.id.tvRecipeName;
import static com.eleven.group.myrecipiebook.R.id.tvTotalTime;

public class RecipeDetailActivity extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeName;
    TextView totalTime;
    TextView rating;

    ListView ingredients;
    ArrayAdapter<String> adapter;
    Toolbar toolbar;
    RecipeResponse mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        SearchRecipe searchRecipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(searchRecipe.getRecipeName());
        setSupportActionBar(toolbar);
        getRecipeYummly(searchRecipe);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void detailView(RecipeResponse recipe){
        recipeImage = (ImageView) findViewById(ivRecipeImage);
        recipeName = (TextView) findViewById(tvRecipeName);
        totalTime = (TextView) findViewById(tvTotalTime);
        rating = (TextView) findViewById(tvRating);
        ingredients = (ListView) findViewById(lvIngredients);

        String image = recipe.getImage();
        Glide.with(getApplicationContext()).load(image).into(recipeImage);
        recipeName.setText(recipe.getName());
        totalTime.setText(recipe.getTotalTime());
        rating.setText(""+recipe.getRating());

        // Create the adapter to convert the array to views
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipe.getIngredients());
        ingredients.setAdapter(adapter);  // Attach the adapter to a ListView

        mRecipe = recipe;
    }

    public void getRecipeYummly(SearchRecipe searchRecipe) {
        AsyncHttpClient client = new AsyncHttpClient();
        String strUrl = getString(R.string.YUMMLY_GET_RECIPE_API);
        String appId = getString(R.string.YUMMLY_APP_ID);
        String appKey = getString(R.string.YUMMLY_API_KEY);
        String recipeId = searchRecipe.getRecipeId();

        strUrl = strUrl + "/" + recipeId;
        RequestParams params = new RequestParams();
        params.put("_app_id", appId);
        params.put("_app_key", appKey);

        /*
         * Added By: Jake Rushing
         */
        RecipeUtility.getRecipeService().getRecipe(recipeId, getString(R.string.YUMMLY_APP_ID),
                getString(R.string.YUMMLY_API_KEY)).enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                detailView(response.body());
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });
    }

    /*
     * Created By: Jake Rushing
     */
    private void shareRecipe(){
        Intent shareRecipeIntent = new Intent();
        shareRecipeIntent.setAction(Intent.ACTION_SEND);
        shareRecipeIntent.putExtra(Intent.EXTRA_TITLE, "Check out this recipe that I found on My Recipes Book");
        shareRecipeIntent.putExtra(Intent.EXTRA_TEXT, setTextForSharing());
        shareRecipeIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareRecipeIntent, getResources().getString(R.string.share_intent_text)));
    }

    private String setTextForSharing(){
        StringBuilder sb = new StringBuilder(mRecipe.getName());
        sb.append("\n \n");
        sb.append("Ingredients: \n \n");
        try {
            JSONArray array = new JSONArray(mRecipe.getIngredients());
            for (int i = 0; i < array.length(); i++){
                sb.append("-").append(array.getString(i)).append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /*
     * Created By: Jake Rushing
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe_detail, menu);

        MenuItem item = menu.findItem(R.id.menu_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        return super.onCreateOptionsMenu(menu);
    }

    /*
     * Created By: Jake Rushing
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_share:
                shareRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
