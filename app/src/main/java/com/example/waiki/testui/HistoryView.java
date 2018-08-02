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
import android.widget.AdapterView;
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

public class HistoryView extends AppCompatActivity {
    TextView msgBox;
    //Button sendBut;
    Thread clientThread;
    List<History> arrayViewHistory;
    ListView historyList;
    EditText editText;
    private static final int SOCKET_TIMEOUT = 5000;
    Socket clientSocket;

//    List<Restaurant> arraySearchRestaurant;
//    ListView searchListView;

    String host = "192.168.43.24";
    int port = 12345;
    String id;
    AlertDialog.Builder builder;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("History");
        setContentView(R.layout.history_layout);
        Intent intent = getIntent();
        id = intent.getStringExtra("user_id");
        host=intent.getStringExtra("host");
        arrayViewHistory = new ArrayList<>();
        historyList = findViewById(R.id.historyList);

//        arraySearchRestaurant=new ArrayList<>();
//        searchListView=findViewById(R.id.searchList);

        editText = findViewById(R.id.editText);


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


                String msg = "view+" + id;
                BufferedReader in = null;
                try {
                    clientSocket = new Socket(host, port);

                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    //DataInputStream is=new DataInputStream(clientSocket.getInputStream());
                    os.writeUTF(msg);

                    os.flush();

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = "";

                    arrayViewHistory.clear();
                    while ((line = in.readLine()) != null) {
                        serverMsg = line;
                        String[] parts = serverMsg.split(":");
                        History his = new History(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                        arrayViewHistory.add(his);
                    }

                    if (in != null) {
                        in.close();
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                loadSearchResult();

                            } catch (Exception e) {

                            }
                            // Thread.currentThread().stop();
                        }
                    });

                    os.close();
                    clientSocket.close();

                } catch (IOException e) {

                } finally {


                    //Thread.currentThread().stop();
                }
            }
        });
        clientThread.start();
//        if (arrayViewHistory.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "App is not connected to server", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "Please make sure enter correct server ip", Toast.LENGTH_LONG).show();
//        }

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String parameter = (arrayViewHistory.get(position)).getResName();

                Intent intent = new Intent(HistoryView.this, SelectedRestaurant.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                intent.putExtra("clickedRestaurantName", parameter);
                intent.putExtra("host", host);
                startActivity(intent);
            }
        });


        //search button listener


    }


    /*Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            msgBox.setText(msg.toString());
        }
    };*/

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
        adapter = new HistoryAdapter(getApplicationContext(), R.layout.history_list, arrayViewHistory);
        historyList.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Done Load search result", Toast.LENGTH_SHORT).show();
    }


}
