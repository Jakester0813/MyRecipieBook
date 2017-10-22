package com.eleven.group.myrecipiebook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.activity.RecipesMeals;

public class ViewPagerFragmentMeals extends Fragment {
    public static final String KEY_RECIPE_INDEX = "recipe_index";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int index = getArguments().getInt(KEY_RECIPE_INDEX);

        getActivity().setTitle(RecipesMeals.names1[index]);
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        final CheckBoxesFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_INDEX, index);
        ingredientsFragment.setArguments(bundle);

        final CheckBoxesFragment directionsFragment = new DirectionsFragment();
        bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_INDEX, index);
        directionsFragment.setArguments(bundle);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return position == 0 ? ingredientsFragment : directionsFragment;
            } // getItem()

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? getString(R.string.ingredients) : getString(R.string.directions);
            } // getPageTitle()

            @Override
            public int getCount() {
                return 2;
            } // getCount()
        }); // FragmentPagerAdapter()

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    } // onCreateView()

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
    } // onStop()
} // ViewPagerFragment
