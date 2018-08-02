package com.example.waiki.testui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pang on 10/12/2017.
 */

public class User_Login extends AppCompatActivity {

    EditText input_id;
    EditText input_ip;
    Button login_button;
    TextView current_ip;
    TextView new_ip;
    Thread clientThread;
    Socket clientSocket;
    static String host = "192.168.43.24";
    int port = 12345;
    List<User_info> user_array;
    User_info user_list=new User_info();

    static String user_id;
    String birthday,address,race;
    char gender;
    boolean vegen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        input_id = (EditText) findViewById(R.id.inputUserId);
        input_ip=(EditText)findViewById(R.id.inputIP);
        current_ip=(TextView)findViewById(R.id.currentIP);
        new_ip=(TextView)findViewById(R.id.newIP);
        login_button = (Button) findViewById(R.id.login_button);
        user_array = new ArrayList<>();

        current_ip.setText("Current IP : "+host);
    }

    public void loginValidate(View view) {

        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String serverMsg = "";
                String msg = "login+"+input_id.getText().toString();



                if(input_ip.getText().toString().isEmpty()==false){
                    host=input_ip.getText().toString();

                }

                BufferedReader in = null;
                try {
                    clientSocket = new Socket(host, port);

                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

                    os.writeUTF(msg);

                    os.flush();

                    String[] data = null;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = "";

                    user_array.clear();
                    while ((line = in.readLine()) != null) {
                        serverMsg = line;
                        String[] parts = serverMsg.split(":");

                        user_list = new User_info(parts[0], parts[1].charAt(0), parts[2],parts[3], parts[4], Boolean.parseBoolean(parts[5]));

                        //user_array.add(user);
                        //nextValidate(user_array);
                    }

                    if (in != null) {
                        in.close();

                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                nextValidate();

                            }catch(Exception e){

                            }
                            // Thread.currentThread().stop();
                        }
                    });

                    os.close();
                    clientSocket.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        clientThread.start();
    }

    public void nextValidate() {
        String login_id = input_id.getText().toString();


        user_id=user_list.getUser_id();
        gender=user_list.getGender();
        birthday=user_list.getBirthday();
        address=user_list.getAddr();
        race=user_list.getRace();
        vegen=user_list.isVegen();

        User_info user_profile = new User_info(user_id,gender,birthday,address,race,vegen);

        nextLogin(user_profile);

    }


    public void nextLogin(User_info user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("host",host);
        intent.putExtra("port",12345);
        intent.putExtra("user_id",user.getUser_id());
        intent.putExtra("gender",user.getGender());
        intent.putExtra("birthday",user.getBirthday());
        intent.putExtra("address",user.getAddr());
        intent.putExtra("race",user.getRace());
        intent.putExtra("vegen",user.isVegen());
        startActivity(intent);
        finish();
    }



}
