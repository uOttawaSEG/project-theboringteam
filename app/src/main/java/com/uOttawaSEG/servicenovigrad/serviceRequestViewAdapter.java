package com.uOttawaSEG.servicenovigrad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class serviceRequestViewAdapter extends RecyclerView.Adapter<serviceRequestViewAdapter.ViewHolder> {

    private final ArrayList<String> serviceName;
    private final ArrayList<Boolean> isApproved;
    Context context;
    public serviceRequestViewAdapter(Context context, ArrayList<String> serviceName, ArrayList<Boolean> isApproved){
        this.serviceName = serviceName;
        this.isApproved = isApproved;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.service_request_row,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.serviceRequestName.setText("Service Name: " + serviceName.get(position));
        holder.isApprovedTxt.setText("Approved: " + isApproved.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you want to approve this service?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.isApprovedTxt.setText("Approved: TRUE");
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.isApprovedTxt.setText(("Approved: FALSE"));
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView serviceRequestName;
        TextView isApprovedTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceRequestName = itemView.findViewById(R.id.serviceRequestName);
            isApprovedTxt = itemView.findViewById(R.id.isApproved);
        }
    }
}

