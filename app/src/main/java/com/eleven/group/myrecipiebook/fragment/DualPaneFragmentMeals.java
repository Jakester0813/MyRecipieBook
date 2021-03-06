package com.eleven.group.myrecipiebook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.activity.RecipesMeals;

public class DualPaneFragmentMeals extends Fragment {
    private static final String INGREDIENTS_FRAGMENT = "ingredients_fragment";
    private static final String DIRECTIONS_FRAGMENT = "directions_fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt(ViewPagerFragment.KEY_RECIPE_INDEX);

        getActivity().setTitle(RecipesMeals.names1[index]);
        View view = inflater.inflate(R.layout.fragment_dual_pane, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();

        IngredientsFragmentMeals savedIngredientsFragment = (IngredientsFragmentMeals) fragmentManager
                .findFragmentByTag(INGREDIENTS_FRAGMENT);

        if (savedIngredientsFragment == null) {
            final IngredientsFragmentMeals ingredientsFragment = new IngredientsFragmentMeals();
            Bundle bundle = new Bundle();
            bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
            ingredientsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.leftPlaceholder, ingredientsFragment, INGREDIENTS_FRAGMENT)
                    .commit();
        } // if

        DirectionsFragment savedDirectionsFragment = (DirectionsFragment) fragmentManager
                .findFragmentByTag(DIRECTIONS_FRAGMENT);

        if (savedDirectionsFragment == null) {
            final DirectionsFragmentMeals directionsFragment = new DirectionsFragmentMeals();
            Bundle bundle = new Bundle();
            bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
            directionsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.rightPlaceholder, directionsFragment, DIRECTIONS_FRAGMENT)
                    .commit();
        } // if

        return view;
    } // onCreateView()

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
    } // onStop()
} // DualPaneFragment