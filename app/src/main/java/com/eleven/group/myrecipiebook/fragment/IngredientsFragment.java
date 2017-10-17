package com.eleven.group.myrecipiebook.fragment;

import com.eleven.group.myrecipiebook.activity.Recipes;

public class IngredientsFragment extends CheckBoxesFragment {
    @Override
    protected String[] getContents(int index) {
        return Recipes.ingredients[index].split("`");
    } // getContents()
} // IngredientsFragment
