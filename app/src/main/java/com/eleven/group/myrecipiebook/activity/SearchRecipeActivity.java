package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.adapter.RecipeAdapter;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.eleven.group.myrecipiebook.model.SearchRecipe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchRecipeActivity extends AppCompatActivity{

    ArrayList<SearchRecipe> recipes;
    RecipeAdapter adapter;
    RecyclerView listRecipes;
    StaggeredGridLayoutManager gridLayoutManager;

    Recipe recipe;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra("getRecipe"));
        setupViews();
    }

    public void setupViews(){
        recipes = new ArrayList<>();

        listRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
        //hook up listener for grid click
        RecipeAdapter.RecyclerViewClickListener listener = new RecipeAdapter.RecyclerViewClickListener(){
            @Override
            public void recyclerViewListClicked(View v, int position) {
                Intent i = new Intent(SearchRecipeActivity.this, RecipeDetailActivity.class);
                SearchRecipe searchRecipe = recipes.get(position);
                i.putExtra("recipe", Parcels.wrap(searchRecipe));
                startActivity(i);
            }
        };

        adapter = new RecipeAdapter(this, recipes, listener);
        listRecipes.setAdapter(adapter);
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        listRecipes.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newQuery) {
                query = newQuery;
                searchRecipesYummly(query, 0);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.calculator) {
            Intent i = new Intent(SearchRecipeActivity.this, CalorieCalculator.class);
            startActivity(i);
        }
        else if(id == R.id.macros){
            Intent i = new Intent(SearchRecipeActivity.this, MacrosCalculation.class);
            i.putExtra("macroRecipe", Parcels.wrap(recipe));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchRecipesYummly(String query, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String strUrl = getString(R.string.YUMMLY_SEARCH_RECIPE_API);
        String appId = getString(R.string.YUMMLY_APP_ID);
        String appKey = getString(R.string.YUMMLY_API_KEY);

        RequestParams params = new RequestParams();
        params.put("_app_id", appId);
        params.put("_app_key", appKey);
        params.put("q",query);
        params.put("start", page);

        client.get(strUrl, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("searchRecipesResponse:",response.toString());

                JSONArray recipeJsonResults = null;
                try{
                    recipeJsonResults = response.getJSONArray("matches");
                    Log.d("searchRecipeMatches:", recipeJsonResults.toString());
                    recipes.addAll(SearchRecipe.fromJSONArray(recipeJsonResults));
                    // making changes directly to adapter modifies the underline data and add it to array list
                    adapter.notifyDataSetChanged();
                    Log.d("searchRecipesYummly:", "No.of recipes recieved = " + recipes.size());
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
