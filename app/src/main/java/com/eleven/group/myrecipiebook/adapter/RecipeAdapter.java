package com.eleven.group.myrecipiebook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.model.Recipe;
import com.eleven.group.myrecipiebook.model.SearchRecipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public ImageView searchRecipeImage;
        public TextView searchRecipeName;

        public ViewHolder(View searchRecipe) {
            super(searchRecipe);
            searchRecipeImage = (ImageView) searchRecipe.findViewById(R.id.ivRecipeImage);
            searchRecipeName = (TextView) searchRecipe.findViewById(R.id.tvRecipeName);
            searchRecipe.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }

    private List<SearchRecipe> recipeList;
    private Context context;
    private static RecyclerViewClickListener itemListener;

    public RecipeAdapter(Context context, List<SearchRecipe> recipeList, RecyclerViewClickListener itemListener){
        this.recipeList = recipeList;
        this.context = context;
        this.itemListener = itemListener;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView;
        RecipeAdapter.ViewHolder viewHolder;
        recipeView = inflater.inflate(R.layout.recipe_item_list, parent, false);
        viewHolder = new RecipeAdapter.ViewHolder(recipeView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder viewHolder, int position){
        SearchRecipe searchRecipe = recipeList.get(position);

        viewHolder.searchRecipeName.setText(searchRecipe.getRecipeName());
        ImageView imageView = viewHolder.searchRecipeImage;
        imageView.setImageResource(0);
        String imageUrl = searchRecipe.getRecipeImage();
        Glide.with(context).load(imageUrl).into(imageView);
    }

    @Override
    public int getItemCount(){
        return recipeList.size();
    }

}
