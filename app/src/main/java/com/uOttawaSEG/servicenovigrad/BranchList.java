package com.uOttawaSEG.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BranchList extends ArrayAdapter<Branch>{
    private Activity context;
    List<Branch> branches;

    public BranchList(Activity context, List<Branch> branches) {
        super(context, R.layout.branch_listview, new ArrayList(branches));

        this.context = context;
        this.branches = branches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.branch_listview, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.branchName);

        Branch branch = branches.get((position));
        textViewName.setText(branch.getName());
        return listViewItem;
    }
}
