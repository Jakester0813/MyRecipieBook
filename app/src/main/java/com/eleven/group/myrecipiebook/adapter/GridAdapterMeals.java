package com.eleven.group.myrecipiebook.adapter;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.fragment.GridFragment;
import com.eleven.group.myrecipiebook.fragment.GridFragmentMeals;

public class GridAdapterMeals extends RecyclerAdapterMeals {

    private final GridFragmentMeals.OnRecipeSelectedInterface listener;

    public GridAdapterMeals(GridFragmentMeals.OnRecipeSelectedInterface listener) {
        this.listener = listener;
    } // GridAdapter()

    @Override
    protected int getLayoutId() {
        return R.layout.grid_meals;
    } // getLayoutId()

    @Override
    protected void onRecipeSelected(int index) {
        listener.onGridRecipeSelected(index);
    } // onRecipeSelected()

} // GridAdapter