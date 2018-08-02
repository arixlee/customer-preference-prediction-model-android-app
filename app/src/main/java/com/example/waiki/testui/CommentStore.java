package com.example.waiki.testui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by waiki on 10/25/2017.
 */

public class CommentStore extends AppCompatActivity{
    Thread clientThread;
    Socket clientSocket;
    String host="192.168.0.111";
    int port=12345;

    EditText comment_leave;
    String comment;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
    Date date = new Date();
    Intent intent_1;
    String getDate;
    String id;
    String resID;
    RatingBar rating;
    int rate;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater factory = getLayoutInflater();

        final View anotherView = factory.inflate(R.layout.custome_restaurant, null);
        comment_leave=anotherView.findViewById(R.id.commentText_1);
        rating=anotherView.findViewById(R.id.rating);
        rate=rating.getNumStars();
        getDate=date.toString();

        intent_1=getIntent();

    }
}