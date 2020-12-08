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

public class ServiceList extends ArrayAdapter<Service> {
        private Activity context;
        private List<Service> services;

        public ServiceList(Activity context, List<Service> services) {
                super(context, R.layout.activity_list, new ArrayList(services));
                this.context = context;
                this.services = services;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View listViewItem = inflater.inflate(R.layout.activity_list, null, true);
                TextView textViewName = listViewItem.findViewById(R.id.serviceName);

                Service product = services.get((position));
                textViewName.setText(product.getName());
                return listViewItem;
        }
}

