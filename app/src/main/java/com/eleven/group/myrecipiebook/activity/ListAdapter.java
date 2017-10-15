package com.eleven.group.myrecipiebook;

public class ListAdapter extends RecyclerAdapter {

    private final ListFragment.OnRecipeSelectedInterface listener;

    public ListAdapter(ListFragment.OnRecipeSelectedInterface listener) {
        this.listener = listener;
    } // ListAdapter()

    @Override
    protected int getLayoutId() {
        return R.layout.list_item;
    } // getLayoutId()

    @Override
    protected void onRecipeSelected(int index) {
        listener.onListRecipeSelected(index);
    } // onRecipeSelected()

} // ListAdapter