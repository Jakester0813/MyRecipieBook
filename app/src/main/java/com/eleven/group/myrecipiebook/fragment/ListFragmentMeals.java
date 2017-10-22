package com.eleven.group.myrecipiebook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.adapter.ListAdapterMeals;

public class ListFragmentMeals extends Fragment {

    public interface OnRecipeSelectedInterface {
        void onListRecipeSelected(int index);
    } // OnRecipeSelectedInterface

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LoggingFragment.TAG, "onCreateView");
        OnRecipeSelectedInterface listener = (OnRecipeSelectedInterface) getActivity();
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ListAdapterMeals listAdapter = new ListAdapterMeals(listener);
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    } // onCreateView()
} // ListFragment
