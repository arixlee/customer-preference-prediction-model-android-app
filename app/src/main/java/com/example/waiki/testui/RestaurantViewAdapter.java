package com.example.waiki.testui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by waiki on 10/19/2017.
 */

public class RestaurantViewAdapter extends ArrayAdapter<Restaurant> {
    Socket clientSocket;
    Thread clientThread;
    int port=12345;
    Button sendComment;
    EditText comment_leave;
    String comment;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
    Date date = new Date();
    Intent intent_1;
    String getDate;
    String  id = User_Login.user_id;
    String host=User_Login.host;
    RatingBar rating;
    int rate;

    TextView resName, area, address, minPrice, goodReview, badReview;
    Button commentButton;
    int k;

    public RestaurantViewAdapter(Context context, int resources, List<Restaurant> restaurant) {
        super(context, resources, restaurant);
        //intent_1 = ((Activity) context).getIntent();
       // intent_1.getStringExtra("host");

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Restaurant restaurant = getItem(position);
        //id=(String) convertView.getTag(R.id.pass_tag);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custome_restaurant, parent, false);
        }

        getDate=date.toString();




        resName = (TextView) convertView.findViewById(R.id.resNameText_1);
        area = (TextView) convertView.findViewById(R.id.areaText_1);
        address = (TextView) convertView.findViewById(R.id.addressText_1);
        minPrice = (TextView) convertView.findViewById(R.id.minPriceText_1);
        goodReview = (TextView) convertView.findViewById(R.id.labelNoGoodReviewText_1);
        badReview = (TextView) convertView.findViewById(R.id.noBadReviewText_1);
        commentButton = (Button) convertView.findViewById(R.id.viewComment_1);

        resName.setText("" + restaurant.getResName());
        area.setText("" + restaurant.getArea());
        address.setText("" + restaurant.getAddress());
        minPrice.setText("" + restaurant.getMinPrice());
        goodReview.setText("" + restaurant.getNoGoodReview());
        badReview.setText("" + restaurant.getNoBadReview());

        id=restaurant.getUserID();

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String parameter = restaurant.getResID();
                Intent intent = new Intent(getContext(), CommentView.class);


                intent.putExtra("clickedRestaurantID", parameter);
                intent.putExtra("user_id",id);
                intent.putExtra("host",host);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });



        return convertView;

    }




}


