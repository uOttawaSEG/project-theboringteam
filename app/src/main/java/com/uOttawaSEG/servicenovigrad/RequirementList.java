package com.uOttawaSEG.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequirementList extends ArrayAdapter<Service> {
    private Activity context;
    ArrayList<String> requirements;


    public RequirementList(Activity context, ArrayList<String> requirements) {
        super(context, R.layout.activity_list,new ArrayList(requirements));
        this.context = context;
        this.requirements = requirements;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.serviceName);



        String requirement = requirements.get(position);
        //reqInfo.get(requirements.get(position))
        textViewName.setText(requirement);
        return listViewItem;
    }
}

