package com.eleven.group.myrecipiebook;

public class IngredientsFragment extends CheckBoxesFragment {
    @Override
    protected String[] getContents(int index) {
        return Recipes.ingredients[index].split("`");
    } // getContents()
} // IngredientsFragment
