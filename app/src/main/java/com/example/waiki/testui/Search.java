package com.example.waiki.testui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    TextView msgBox;
    Button sendBut;
    Thread clientThread;
    List<Restaurant> arraySearchRestaurant;
    ListView searchListView;
    EditText editText;
    boolean noResult=false;
    private static final int SOCKET_TIMEOUT = 5000;
    Socket clientSocket;
    String id;



    String host="192.168.43.24";
    int port=12345;
    AlertDialog.Builder builder;
    SearchResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search");
        setContentView(R.layout.search);

        Intent intent_1=getIntent();
        id = intent_1.getStringExtra("user_id");
        host = intent_1.getStringExtra("host");

        //host=i.getStringExtra("host");


        sendBut=findViewById(R.id.sendSearch);

        arraySearchRestaurant=new ArrayList<>();
        searchListView=findViewById(R.id.searchList);
        editText=findViewById(R.id.editText);


        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.chg_ip, null);
        builder.setView(dialogView);
        builder.setMessage("Please enter valid IP address such as: 192.168.0.105");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
                    }
                });

        builder.setNegativeButton(
                "Set IP",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText ipAddress=(EditText)dialogView.findViewById(R.id.ipAddress);

                        host=ipAddress.getText().toString();
                        dialog.cancel();

                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
                    }
                });
        //imgView=(ImageView)findViewById(R.id.imageView);


        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientThread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String serverMsg="";


                        String msg="search+"+editText.getText();
                        BufferedReader in = null;
                        try{
                            clientSocket=new Socket(host,port);

                            DataOutputStream os=new DataOutputStream(clientSocket.getOutputStream());
                            DataInputStream is=new DataInputStream(clientSocket.getInputStream());
                            os.writeUTF(msg);

                            os.flush();

                            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            String line="";

                            arraySearchRestaurant.clear();
                            while ((line = is.readLine()) != null) {
                                noResult=false;

                                try {
                                    serverMsg=line;
                                    String[] parts=serverMsg.split(":");
                                    byte[] bytes = new byte[Integer.parseInt(parts[2])];
                                    is.readFully(bytes);

                                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                                    String resName=parts[0];
                                    resName.concat("\n");
                                    ByteArrayInputStream byteIn = new ByteArrayInputStream(resName.getBytes());
                                    BufferedReader br = new BufferedReader(new InputStreamReader(byteIn));

                                    Restaurant res = new Restaurant(br.readLine(), Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),bmp);
                                    arraySearchRestaurant.add(res);
                                   // in.readLine();






                                   // Restaurant res = new Restaurant(parts[0], Integer.parseInt(parts[1],Integer.parseInt(parts[2],));
                                    //arraySearchRestaurant.add(res);
                                }catch(Exception e){
                                    noResult=true;
                                }

                            }
                            in.close();
                            is.close();
                           // inputStream.read(xbyte);
                            //final InputStream x=clientSocket.getInputStream();
                          //  int size=serverMsg.length();
                          //  final InputStream finalIn=inputStream;
                            final String finalServerMsg=serverMsg;
                            //final InputStream imgStream=clientSocket.getInputStream();
                            if(in!=null){

                            }

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    try {
                                        if(!arraySearchRestaurant.isEmpty()){
                                            loadSearchResult();
                                        }



                                    }catch(Exception e){
                                        //Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_SHORT).show();
                                    }
                                   // Thread.currentThread().stop();
                                }
                            });

                            os.close();
                            clientSocket.close();

                        }catch(IOException e){

                        }finally {


                            //Thread.currentThread().stop();
                        }
                    }
                });
                clientThread.start();
                if(noResult){
                    Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_SHORT).show();
                }

                if(arraySearchRestaurant.isEmpty()&&!noResult){
                    Toast.makeText(getApplicationContext(), "App is not connected to server", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Please make sure enter correct server ip", Toast.LENGTH_LONG).show();
                }


            }
        });  //search button listener


        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String parameter =(arraySearchRestaurant.get(position)).getResName();
                Intent getIntent=getIntent();
                Intent intent = new Intent(getApplicationContext(), SelectedRestaurant.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();
                intent.putExtra("clickedRestaurantName", parameter);
                intent.putExtra("host",host);
                intent.putExtra("user_id",getIntent.getStringExtra("user_id"));
                startActivity(intent);
            }
        });


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

    public void loadSearchResult(){
        adapter=new SearchResultAdapter(getApplicationContext(),R.layout.search_result_list_layout,arraySearchRestaurant);

        searchListView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Done Load search result", Toast.LENGTH_SHORT).show();
    }

}
