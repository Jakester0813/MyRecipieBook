package com.eleven.group.myrecipiebook.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.fragment.DualPaneFragmentMeals;
import com.eleven.group.myrecipiebook.fragment.GridFragmentMeals;
import com.eleven.group.myrecipiebook.fragment.ListFragmentMeals;
import com.eleven.group.myrecipiebook.fragment.ViewPagerFragmentMeals;

/**
 * Created by siddhatapatil on 10/22/17.
 */

public class MealGridActivity extends AppCompatActivity implements ListFragmentMeals.OnRecipeSelectedInterface, GridFragmentMeals.OnRecipeSelectedInterface
{
    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String VIEW_PAGER_FRAGMENT = "viewpager_fragment";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        GridFragmentMeals savedFragment = (GridFragmentMeals) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);

        // Prevents the fragment from being recreated every time the activty's onCreate() method is called
        if (savedFragment == null) {
            GridFragmentMeals fragment = new GridFragmentMeals();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder1, fragment, LIST_FRAGMENT);
            fragmentTransaction.commit();
        } // if

    }
    @Override
    public void onListRecipeSelected(int index) {
        ViewPagerFragmentMeals fragment = new ViewPagerFragmentMeals();

        // Store the index in the bundle to pass it to the next fragment instead of using a constructor
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragmentMeals.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder1, fragment, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // onListRecipeSelected()

    @Override
    public void onGridRecipeSelected(int index) {
        DualPaneFragmentMeals fragment = new DualPaneFragmentMeals();

        // Store the index in the bundle to pass it to the next fragment instead of using a constructor
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragmentMeals.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder1, fragment, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // onGridRecipeSelected()
}

