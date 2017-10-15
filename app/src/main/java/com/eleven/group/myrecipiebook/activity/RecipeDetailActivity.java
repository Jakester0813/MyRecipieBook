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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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

import cz.msebera.android.httpclient.Header;

public class RecipeDetailActivity extends AppCompatActivity {

    ImageView ivRecipeImage;
    TextView tvTitle;
    TextView tvPublisher;
    TextView tvSocialRank;
    Toolbar toolbar;
    Recipe mRecipe;

    //TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getTitle());
        setSupportActionBar(toolbar);
        getRecipeFood2Fork(recipe);

    }

    public void detailView(Recipe recipe){
        ivRecipeImage = (ImageView) findViewById(R.id.ivRecipeImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        tvSocialRank = (TextView) findViewById(R.id.tvSocialRank);
        //tvIngredients = (TextView) findViewById(R.id.tvIngredients);

        String image = recipe.getImageUrl();
        Glide.with(getApplicationContext()).load(image).into(ivRecipeImage);
        tvTitle.setText(recipe.getTitle());
        tvPublisher.setText(recipe.getPublisher());
        tvSocialRank.setText(""+recipe.getSocialRank());
        mRecipe = recipe;
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

    private void shareRecipe(){
        Intent shareRecipeIntent = new Intent();
        shareRecipeIntent.setAction(Intent.ACTION_SEND);
        shareRecipeIntent.putExtra(Intent.EXTRA_TITLE, "Check out this recipe that I found on My Recipes Book");
        shareRecipeIntent.putExtra(Intent.EXTRA_TEXT, setTextForSharing());
        shareRecipeIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareRecipeIntent, getResources().getString(R.string.share_intent_text)));
    }

    private String setTextForSharing(){
        StringBuilder sb = new StringBuilder(mRecipe.getTitle());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe_detail, menu);

        MenuItem item = menu.findItem(R.id.menu_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        return super.onCreateOptionsMenu(menu);
    }

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
