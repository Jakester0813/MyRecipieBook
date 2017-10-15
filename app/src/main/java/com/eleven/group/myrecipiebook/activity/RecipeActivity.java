package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RecipeActivity extends AppCompatActivity{

    //private InfiniteScrollListener scrollListener;

    ArrayList<Recipe> recipes;
    RecipeAdapter adapter;
    RecyclerView listRecipes;
    StaggeredGridLayoutManager gridLayoutManager;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews(){
        recipes = new ArrayList<>();

        listRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
        //hook up listener for grid click
        RecipeAdapter.RecyclerViewClickListener listener = new RecipeAdapter.RecyclerViewClickListener(){
            @Override
            public void recyclerViewListClicked(View v, int position) {
                // create an intent to display the article
                Intent i = new Intent(RecipeActivity.this, RecipeDetailActivity.class);
                // get the article to diasplay
                Recipe recipe = recipes.get(position);
                // pass that article into intent
                i.putExtra("recipe", Parcels.wrap(recipe));
                // launch the activity
                startActivity(i);
            }
        };

        adapter = new RecipeAdapter(this, recipes, listener);
        // Attach the adapter to the recyclerview to populate items
        listRecipes.setAdapter(adapter);
        // Set StaggeredGridlayout manager to position the items
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        listRecipes.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newQuery) {
                // performing query
                query = newQuery;
                searchRecipesFood2Fork(query, 0);
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

    public void searchRecipesFood2Fork(String query, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String strUrl = getString(R.string.FOOD2FORK_SEARCH_API);
        String apiKey = getString(R.string.FOOD2FORK_API_KEY);

        RequestParams params = new RequestParams();
        params.put("key", apiKey);
        params.put("page", page);
        params.put("q",query);

        client.get(strUrl, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG",response.toString());

                JSONArray recipeJsonResults = null;
                try{
                    recipeJsonResults = response.getJSONArray("recipes");
                    Log.d("searchRecipesFood2Fork:", recipeJsonResults.toString());
                    recipes.addAll(Recipe.fromJSONArray(recipeJsonResults));
                    // making changes directly to adapter modifies the underline data and add it to array list
                    adapter.notifyDataSetChanged();
                    Log.d("searchRecipesFood2Fork:", "Number of articles recieved = " + recipes.size());
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
