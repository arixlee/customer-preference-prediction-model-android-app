package com.example.waiki.testui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int SOCKET_TIMEOUT = 5000;
    Intent intent_1;
    Thread clientThread;
    int re;
    TextView msgBox;
    EditText editText;
    List<Restaurant> arraySearchRestaurant;
    ListView searchList;
    SearchResultAdapter adapter;
    String host;
    int port = 12345;
    Socket clientSocket;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Food Hunting~Are you ok?");
        intent_1=getIntent();
        //user_id = User_Login.user_id;
        //host=User_Login.host;
        user_id=intent_1.getStringExtra("user_id");
        host=intent_1.getStringExtra("host");
        ImageButton button_restaurant = (ImageButton) findViewById(R.id.button_1);
        ImageButton button_search = (ImageButton) findViewById(R.id.button_2);
        ImageButton button_viewhis = (ImageButton) findViewById(R.id.button_3);
        ImageButton button_profile = (ImageButton) findViewById(R.id.button_4);

        ////////////////Button Restaurant//////////////////////////////////
        button_restaurant.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RestaurantView.class);
                intent.putExtra("host",host);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });

        //////////////////////////Search Button/////////////////////////

        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                intent.putExtra("host", host);
                intent.putExtra("user_id",user_id);
                //intent.putExtra("port", port);
                startActivity(intent);
                //setContentView(R.layout.test_search);

            }

        });

        //////////////////////////View History Button/////////////////////////

        button_viewhis.setOnClickListener(new View.OnClickListener() {
            String user_id;

            public void onClick(View v) {
                intent_1 = getIntent();
                user_id = intent_1.getStringExtra("user_id");
                Intent intent = new Intent(MainActivity.this, HistoryView.class);
                intent.putExtra("host", host);
//
               intent.putExtra("user_id", user_id);
                startActivity(intent);

            }
        });

        //////////////////////////Profile Button/////////////////////////

        button_profile.setOnClickListener(new View.OnClickListener() {
            String user_id, birthday, address, race;
            char gender;
            boolean vegen;

            @Override
            public void onClick(View view) {
                intent_1 = getIntent();
                user_id = intent_1.getExtras().getString("user_id");
                gender = intent_1.getExtras().getChar("gender");
                birthday = intent_1.getExtras().getString("birthday");
                address = intent_1.getExtras().getString("address");
                race = intent_1.getExtras().getString("race");
                vegen = intent_1.getExtras().getBoolean("vegen");
                Intent intent = new Intent(MainActivity.this, Profile.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("gender", gender);
                intent.putExtra("birthday", birthday);
                intent.putExtra("address", address);
                intent.putExtra("race", race);
                intent.putExtra("vegen", vegen);

                startActivity(intent);
            }
        });

    }


    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this).setTitle("Exit")
                .setMessage("Are You Sure to Exit Apps?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", null).show();

    }

    protected void onDestroy() {
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "terminate+" + "bye";
                try {
                    clientSocket = new Socket(host, port);
                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    os.writeUTF(msg);
                    os.flush();
                    os.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();
        super.onDestroy();
    }


}




