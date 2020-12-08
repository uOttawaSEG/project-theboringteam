package com.uOttawaSEG.servicenovigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ServiceRequestList extends ArrayAdapter<ServiceRequest> {
    private Activity context;
    private List<ServiceRequest> requests;

    public ServiceRequestList(Activity context, List<ServiceRequest> requests) {
        super(context, R.layout.activity_list, new ArrayList(requests));

        this.context = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.serviceName);

        ServiceRequest request= requests.get((position));
        textViewName.setText(request.getUserID() + " Requesting Service " + request.getServiceID());
        return listViewItem;
    }
}

