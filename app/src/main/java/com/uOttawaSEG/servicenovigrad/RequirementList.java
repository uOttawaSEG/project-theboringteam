package com.uOttawaSEG.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RequirementList extends ArrayAdapter<Service> {
    private Activity context;
    Service service;

    public RequirementList(Activity context, Service service) {
        super(context, R.layout.activity_list);
        this.context = context;
        this.service = service;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.serviceName);


        ArrayList product = service.getReqInfo();
        textViewName.setText((String) product.get(position));
        return listViewItem;
    }
}

