package com.example.waiki.testui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiki on 10/13/2017.
 */

public class Profile extends AppCompatActivity {
    Thread clientThread;
    Socket clientSocket;
    String host = "192.168.0.104";
    int port = 12345;

    List<User_info> user_array;

    TextView pro_id, pro_gender, pro_bd, pro_addr, pro_race, pro_vegen;
    String user_id, birthday, address, race;
    char gender;
    boolean vegen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting);
        user_array = new ArrayList<>();
        setTitle("User Profile");
        Intent intent = getIntent();

        host=User_Login.host;

        pro_id = findViewById(R.id.pro_text_id);
        pro_gender = findViewById(R.id.pro_text_gender);
        pro_bd = findViewById(R.id.pro_text_birthday);
        pro_addr = findViewById(R.id.pro_text_addr);
        pro_race = findViewById(R.id.pro_text_race);
        pro_vegen = findViewById(R.id.pro_text_vegen);

        user_id=User_Login.user_id;
        //user_id = intent.getStringExtra("user_id");
        gender = intent.getCharExtra("gender", gender);
        birthday = intent.getStringExtra("birthday");
        address = intent.getStringExtra("address");
        race = intent.getStringExtra("race");
        vegen = intent.getBooleanExtra("vegen", vegen);

        pro_id.setText("ID              : " + user_id);
        pro_gender.setText("Gender     : " + String.valueOf(gender));
        pro_bd.setText("Birthday   : " + birthday);
        pro_addr.setText("Address  : " + address);
        pro_race.setText("Race         : " + race);
        pro_vegen.setText("Vegen       : " + String.valueOf(vegen));
    }
}



