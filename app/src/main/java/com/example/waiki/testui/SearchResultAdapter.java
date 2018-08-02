package com.example.waiki.testui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Fung Desk on 05-Oct-17.
 */

public class SearchResultAdapter extends ArrayAdapter<Restaurant> {
    TextView resName,goodReview;
    ImageView imageView;

    public SearchResultAdapter(Context context, int resources, List<Restaurant> restaurant){
        super(context,resources,restaurant);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custome_search_layout, parent, false);
        }
        // Lookup view for data population
        resName = (TextView) convertView.findViewById(R.id.restaurantNameText);
        goodReview = (TextView) convertView.findViewById(R.id.goodReviewText);
        imageView=(ImageView)convertView.findViewById(R.id.imageView);

        // Populate the data into the template view using the data object
        resName.setText(restaurant.getResName());
        goodReview.setText("Good Reviews: "+ Integer.toString(restaurant.getNoGoodReview()));
        imageView.setImageBitmap(restaurant.getResImage());

        // Return the completed view to render on screen
        return convertView;
    }


}
