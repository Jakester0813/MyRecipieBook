package com.eleven.group.myrecipiebook;

public class DirectionsFragment extends CheckBoxesFragment {
    @Override
    protected String[] getContents(int index) {
        return Recipes.directions[index].split("`");
    } // getContents()
} // DirectionsFragment
