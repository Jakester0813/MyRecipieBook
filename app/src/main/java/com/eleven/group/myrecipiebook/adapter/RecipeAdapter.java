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

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public ImageView recipeImage;
        public TextView recipeTitle;

        public ViewHolder(View recipe) {
            super(recipe);
            recipeImage = (ImageView) recipe.findViewById(R.id.ivRecipeImage);
            recipeTitle = (TextView) recipe.findViewById(R.id.tvRecipeTitle);
            recipe.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }

    private List<Recipe> recipeList;
    private Context context;
    private static RecyclerViewClickListener itemListener;

    public RecipeAdapter(Context context, List<Recipe> recipeList, RecyclerViewClickListener itemListener){
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
        Recipe recipe = recipeList.get(position);

        viewHolder.recipeTitle.setText(recipe.getTitle());
        ImageView imageView = viewHolder.recipeImage;
        imageView.setImageResource(0);
        String imageUrl = recipe.getImageUrl();
        Glide.with(context).load(imageUrl).into(imageView);
    }

    @Override
    public int getItemCount(){
        return recipeList.size();
    }

}
