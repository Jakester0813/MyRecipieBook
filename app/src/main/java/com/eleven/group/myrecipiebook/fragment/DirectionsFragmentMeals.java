package com.eleven.group.myrecipiebook.fragment;

import com.eleven.group.myrecipiebook.activity.RecipesMeals;

public class DirectionsFragmentMeals extends CheckBoxesFragment {
    @Override
    protected String[] getContents(int index) {
        return RecipesMeals.directions[index].split("`");
    } // getContents()
} // DirectionsFragment
