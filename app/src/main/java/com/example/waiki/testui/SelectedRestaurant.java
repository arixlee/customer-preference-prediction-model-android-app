package com.example.waiki.testui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SelectedRestaurant extends AppCompatActivity {
    Thread clientThread;
    TextView resNameText,areaText,addressText,minPriceText,noGoodReviewText,noBadReviewText,dishNameText;
    Restaurant res;
    String dishName;
    long startTime;
    String resID;
    String host="192.168.43.24";
    int port=12345;
    Intent intent;
    String id;
    Button commentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_restaurant);

        startTime= System.currentTimeMillis();
        resNameText=(TextView)findViewById(R.id.resNameText);
        areaText=(TextView)findViewById(R.id.areaText);
        addressText=(TextView)findViewById(R.id.addressText);
        minPriceText=(TextView)findViewById(R.id.minPriceText);
        noGoodReviewText=(TextView)findViewById(R.id.noGoodReviewText);
        noBadReviewText=(TextView)findViewById(R.id.noBadReviewText);
        dishNameText=(TextView)findViewById(R.id.dishNameText);
        commentButton=(Button)findViewById(R.id.viewComment);


        intent = getIntent();
        final String clickedRestaurantName = intent.getStringExtra("clickedRestaurantName");
        id = intent.getStringExtra("user_id");
        host=intent.getStringExtra("host");
        setTitle(clickedRestaurantName);

        clientThread=new Thread(new Runnable() {
            @Override
            public void run() {
                String serverMsg="";

                String msg="clickRestaurant+"+clickedRestaurantName;
                BufferedReader in = null;
                try{
                    Socket clientSocket=new Socket(host,port);

                    DataOutputStream os=new DataOutputStream(clientSocket.getOutputStream());
                    //DataInputStream is=new DataInputStream(clientSocket.getInputStream());
                    os.writeUTF(msg);

                    os.flush();

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line="";
                    //byte[] xbyte=new byte[4096];
                    //InputStream inputStream=clientSocket.getInputStream();

                    while ((line = in.readLine()) != null) {
                        serverMsg=line;
                        String[] parts=serverMsg.split(":");
                        res=new Restaurant(parts[0],parts[1],parts[2],Double.parseDouble(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]));
                        dishName=parts[6];
                        resID=parts[7];


                    }

                    // inputStream.read(xbyte);
                    //final InputStream x=clientSocket.getInputStream();
                    //  int size=serverMsg.length();
                    //  final InputStream finalIn=inputStream;
                    final String finalServerMsg=serverMsg;
                    //final InputStream imgStream=clientSocket.getInputStream();
                    if(in!=null){
                        in.close();
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                resNameText.setText(res.getResName());
                                areaText.setText(res.getArea());
                                addressText.setText(res.getAddress());
                                minPriceText.setText(Double.toString(res.getMinPrice()));
                                noGoodReviewText.setText(Integer.toString(res.getNoGoodReview()));
                                noBadReviewText.setText(Integer.toString(res.getNoBadReview()));
                                dishNameText.setText(dishName);
                            }catch(Exception e){

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

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                View parentRow = (View) view.getParent();
//                ListView listView = (ListView) parentRow.getParent();
//                final int position = listView.getPositionForView(parentRow);
//                String parameter = (arrayViewRestaurant.get(position)).getResName();
                //String parameter = resID;
                Intent intent = new Intent(SelectedRestaurant.this, CommentView.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                intent.putExtra("clickedRestaurantID", resID);
                intent.putExtra("user_id",id);
                intent.putExtra("host",host);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onPause() {
        clientThread=new Thread(new Runnable() {
            @Override
            public void run() {
                //String host="192.168.0.105";
                //int port=12345;
                long timer=(System.currentTimeMillis())-startTime;
                double finalTimer=timer/1000.0/60;
                double likelihood=0;
                if(finalTimer<10){
                    likelihood=finalTimer*0.25;
                }
                String msg="history+"+id+"+"+resID+"+"+Double.toString(finalTimer)+"+"+Double.toString(likelihood);

                try{
                    Socket clientSocket=new Socket(host,port);

                    DataOutputStream os=new DataOutputStream(clientSocket.getOutputStream());
                    os.writeUTF(msg);
                    os.flush();
                    os.close();
                    clientSocket.close();

                }catch(IOException e){

                }finally {

                }
            }
        });
        clientThread.start();




        super.onPause();
    }


}
