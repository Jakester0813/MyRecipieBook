package com.eleven.group.myrecipiebook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.activity.Recipes;
import com.eleven.group.myrecipiebook.activity.RecipesMeals;

public abstract class RecyclerAdapterMeals extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new ListViewHolder(view);
    } // onCreateViewHolder()

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    } // onBindViewHolder()

    @Override
    public int getItemCount() {
        return RecipesMeals.names1.length;
    } // getItemCount()

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView1;
        private ImageView imageView1;
        private int index;

        public ListViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.itemText1);
            imageView1 = (ImageView) itemView.findViewById(R.id.itemImage1);

            itemView.setOnClickListener(this);
        } // ListViewHolder()

        public void bindView(int position) {
            index = position;
            textView1.setText(RecipesMeals.names1[position]);
            imageView1.setImageResource(RecipesMeals.resourceIds1[position]);
        } // bindView()

        @Override
        public void onClick(View v) {
            onRecipeSelected(index);
        } // onClick()
    } // ListViewHolder

    protected abstract void onRecipeSelected(int index);
} // ListAdapter