package com.example.waiki.testui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pang on 10/11/2017.
 */

public class HistoryAdapter extends ArrayAdapter<History>{
    TextView resName,viewTime,likelihood;
    public HistoryAdapter(Context context, int resources, List<History> history){
        super(context,resources,history);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        History history = getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.history_custome,parent,false);
        }

//        resName=(TextView)convertView.findViewById(R.id.res_name);
//        viewTime=(TextView)convertView.findViewById(R.id.res_view_time);
//        likelihood=(TextView)convertView.findViewById(R.id.res_likelihood);
//
//

        resName=(TextView)convertView.findViewById(R.id.history_restName);
        viewTime=(TextView)convertView.findViewById(R.id.history_viewTime);
        likelihood=(TextView)convertView.findViewById(R.id.history_likeliHood);

        resName.setText(history.getResName());
        viewTime.setText("Total View Time : "+Double.toString(history.getViewTimer()));
        likelihood.setText("Likelihood      " +
                ": "+Double.toString(history.getLikelihoodValue()));

        return convertView;

    }

}
