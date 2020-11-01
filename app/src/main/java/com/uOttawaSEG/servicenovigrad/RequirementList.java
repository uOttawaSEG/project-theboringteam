package com.uOttawaSEG.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequirementList extends ArrayAdapter<Service> {
    private Activity context;
    Service service;
    ArrayList requirements;

    public RequirementList(Activity context, Service service) {
        super(context, R.layout.activity_list);
        this.context = context;
        this.service = service;

        HashMap infoList = service.getReqInfo();

        ArrayList requirements = new ArrayList();

        String[] keys = new String[infoList.size()];
        infoList.keySet().toArray(keys);

        for(String key : keys){
            requirements.add(infoList.get(key));
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.serviceName);



        textViewName.setText((String) requirements.get(position));
        return listViewItem;
    }
}

