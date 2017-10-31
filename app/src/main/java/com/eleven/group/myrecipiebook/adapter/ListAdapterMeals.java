package com.eleven.group.myrecipiebook.adapter;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.fragment.ListFragment;
import com.eleven.group.myrecipiebook.fragment.ListFragmentMeals;

public class ListAdapterMeals extends RecyclerAdapterMeals {

    private final ListFragmentMeals.OnRecipeSelectedInterface listener;

    public ListAdapterMeals(ListFragmentMeals.OnRecipeSelectedInterface listener) {
        this.listener = listener;
    } // ListAdapter()

    @Override
    protected int getLayoutId() {
        return R.layout.list_meals;
    } // getLayoutId()

    @Override
    protected void onRecipeSelected(int index) {
        listener.onListRecipeSelected(index);
    } // onRecipeSelected()

} // ListAdapter