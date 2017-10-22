package com.eleven.group.myrecipiebook.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.fragment.DualPaneFragment;
import com.eleven.group.myrecipiebook.fragment.GridFragment;
import com.eleven.group.myrecipiebook.fragment.ListFragment;
import com.eleven.group.myrecipiebook.fragment.ViewPagerFragment;

/**
 * Created by siddhatapatil on 10/22/17.
 */

public class SnacksGridActivity extends AppCompatActivity implements ListFragment.OnRecipeSelectedInterface, GridFragment.OnRecipeSelectedInterface
{
    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String VIEW_PAGER_FRAGMENT = "viewpager_fragment";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);
        GridFragment savedFragment = (GridFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);

        // Prevents the fragment from being recreated every time the activty's onCreate() method is called
        if (savedFragment == null) {
            GridFragment fragment = new GridFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder, fragment, LIST_FRAGMENT);
            fragmentTransaction.commit();
        } // if

    }
    @Override
    public void onListRecipeSelected(int index) {
        ViewPagerFragment fragment = new ViewPagerFragment();

        // Store the index in the bundle to pass it to the next fragment instead of using a constructor
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // onListRecipeSelected()

    @Override
    public void onGridRecipeSelected(int index) {
        DualPaneFragment fragment = new DualPaneFragment();

        // Store the index in the bundle to pass it to the next fragment instead of using a constructor
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // onGridRecipeSelected()
}

