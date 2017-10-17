package com.eleven.group.myrecipiebook.fragment;

import com.eleven.group.myrecipiebook.activity.Recipes;

public class DirectionsFragment extends CheckBoxesFragment {
    @Override
    protected String[] getContents(int index) {
        return Recipes.directions[index].split("`");
    } // getContents()
} // DirectionsFragment
