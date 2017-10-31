package com.eleven.group.myrecipiebook.fragment;

import com.eleven.group.myrecipiebook.activity.RecipesMeals;

public class IngredientsFragmentMeals extends CheckBoxesFragment {
    @Override
    protected String[] getContents(int index) {
        return RecipesMeals.ingredients[index].split("`");
    } // getContents()
} // IngredientsFragment
