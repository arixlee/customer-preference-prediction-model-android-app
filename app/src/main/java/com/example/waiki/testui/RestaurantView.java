package com.example.waiki.testui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiki on 10/18/2017.
 */

public class RestaurantView extends AppCompatActivity {
    TextView msgBox;
    //Button sendBut;
    Thread clientThread;
    List<Restaurant> arrayViewRestaurant;
    ListView restaurantList;
    EditText editText;
    private static final int SOCKET_TIMEOUT = 5000;
    Socket clientSocket;
    boolean noResult;

//    List<Restaurant> arraySearchRestaurant;
//    ListView searchListView;

    String host = "192.168.43.24";
    int port = 12345;
    String id;
    AlertDialog.Builder builder;
    RestaurantViewAdapter adapter;
    Button checkComment;
    Button sendComment;
    EditText comment_leave;
    String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("All restaurant");
        setContentView(R.layout.restaurant_layout);
        Intent intent = getIntent();
        id = intent.getStringExtra("user_id");
        host=intent.getStringExtra("host");
        arrayViewRestaurant = new ArrayList<>();
        restaurantList = findViewById(R.id.restaurantList);


        final LayoutInflater factory = getLayoutInflater();

        final View anotherView = factory.inflate(R.layout.custome_restaurant, null);

        checkComment = (Button) anotherView.findViewById(R.id.viewComment_1);

        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.chg_ip, null);
        builder.setView(dialogView);
        builder.setMessage("Please enter valid IP address such as: 192.168.0.105");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        ((ViewGroup) dialogView.getParent()).removeView(dialogView);
                    }
                });

        builder.setNegativeButton(
                "Set IP",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText ipAddress = (EditText) dialogView.findViewById(R.id.ipAddress);

                        host = ipAddress.getText().toString();
                        dialog.cancel();

                        ((ViewGroup) dialogView.getParent()).removeView(dialogView);
                    }
                });
        //imgView=(ImageView)findViewById(R.id.imageView);


        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String serverMsg = "";

                String msg = "getRestList+"+id;
                BufferedReader in = null;
                try {
                    clientSocket = new Socket(host, port);

                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    //DataInputStream is=new DataInputStream(clientSocket.getInputStream());
                    os.writeUTF(msg);

                    os.flush();

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = "";

                    arrayViewRestaurant.clear();
                    while ((line = in.readLine()) != null) {
                        noResult = false;
                        serverMsg = line;
                        String[] parts = serverMsg.split(":");
                        try {
                            Restaurant res = new Restaurant(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]),id);
                            arrayViewRestaurant.add(res);


                            //loadSearchResult();
                        } catch (Exception e) {
                            noResult = true;
                        }

                    }


                    final String finalServerMsg = serverMsg;
                    //final InputStream imgStream=clientSocket.getInputStream();
                    if (in != null) {
                        in.close();
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                loadSearchResult();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                    os.close();
                    clientSocket.close();

                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {


                    //Thread.currentThread().stop();
                }
            }
        });
        clientThread.start();

        if (noResult) {
            Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_SHORT).show();
        }
//        if (arrayViewRestaurant.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "App is not connected to server", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "Please make sure enter correct server ip", Toast.LENGTH_LONG).show();
//        }







    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //here bobo

            AlertDialog alert11 = builder.create();


            alert11.show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void loadSearchResult() {
        adapter = new RestaurantViewAdapter(getApplicationContext(), R.layout.restaurant_list, arrayViewRestaurant);

        restaurantList.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "Done Load search result", Toast.LENGTH_SHORT).show();
    }

}
