package com.eleven.group.myrecipiebook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.eleven.group.myrecipiebook.R;

public abstract class CheckBoxesFragment extends Fragment {
    private static final String KEY_CHECKED_BOXES = "key_checked_boxes";
    private CheckBox[] checkBoxes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt(ViewPagerFragment.KEY_RECIPE_INDEX);
        View view = inflater.inflate(R.layout.fragment_checkboxes, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.checkBoxesLayout);

        String[] contents = getContents(index);

        checkBoxes = new CheckBox[contents.length];
        boolean[] checkedBoxes = new boolean[checkBoxes.length];

        if ((savedInstanceState != null) && (savedInstanceState.getBooleanArray(KEY_CHECKED_BOXES) != null)) {
            checkedBoxes = savedInstanceState.getBooleanArray(KEY_CHECKED_BOXES);
        } // if

        setUpCheckBoxes(contents, linearLayout, checkedBoxes);
        return view;
    } // onCreateView()

    protected abstract String[] getContents(int index);

    private void setUpCheckBoxes(String[] contents, ViewGroup container, boolean[] checkedBoxes) {
        for (int i = 0; i < contents.length; i++) {
            checkBoxes[i] = new CheckBox(getActivity());
            checkBoxes[i].setPadding(8, 16, 8, 16);
            checkBoxes[i].setTextSize(20f);
            checkBoxes[i].setText(contents[i]);
            container.addView(checkBoxes[i]);
            if (checkedBoxes[i]) {
                checkBoxes[i].setChecked(true);
            } // if
        } // for
    } // setUpCheckBoxes()

    @Override
    public void onSaveInstanceState(Bundle outState) {
        boolean[] stateOfCheckBoxes = new boolean[checkBoxes.length];
        for (int i = 0; i < checkBoxes.length; i++) {
            stateOfCheckBoxes[i] = checkBoxes[i].isChecked();
        } // for
        outState.putBooleanArray(KEY_CHECKED_BOXES, stateOfCheckBoxes);
        super.onSaveInstanceState(outState);
    } // onSaveInstanceState()
} // CheckBoxesFragment