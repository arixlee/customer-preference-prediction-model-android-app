package com.example.waiki.testui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by waiki on 10/19/2017.
 */

public class CommentView extends AppCompatActivity {
    //List comment;
    Thread clientThread;
    Socket clientSocket;
    String host="192.168.43.24";
    int port=12345;
    boolean noResult;
    List<Comment> commentArrayList;
    ListView commentList;
    CommentAdapter adapter;

    Button sendComment;
    EditText comment_leave;
    //String comment;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
    Date date = new Date();
    Intent intent_1;
    String getDate=dateFormat.format(date);
    String id;
    String resID;
    RatingBar rating;
    RestaurantViewAdapter adapter_rest;
    int rate;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);
        setTitle("Comments");
        commentList=findViewById(R.id.comment_list_content);
        commentArrayList=new ArrayList<>();
        Intent intent=getIntent();
//        host=intent_1.getStringExtra("host_adapter");
        id = intent.getStringExtra("user_id");
        host=intent.getStringExtra("host");

//        final LayoutInflater factory = getLayoutInflater();
//        final View anotherView = factory.inflate(R.layout.comment_feedback, null);
//        sendComment=anotherView.findViewById(R.id.sendComment_1);
//        comment_leave=anotherView.findViewById(R.id.commentText_1);
//        rating=anotherView.findViewById(R.id.rating);

        sendComment=findViewById(R.id.sendComment_1);
        comment_leave=findViewById(R.id.commentText_1);
        rating=findViewById(R.id.rating);



        clientThread=new Thread(new Runnable() {
            @Override
            public void run() {
                String serverMsg="";
                Intent intent = getIntent();
                String parameter=intent.getStringExtra("clickedRestaurantID");

                String msg="getRestComment+"+parameter;
                BufferedReader in = null;
                try{
                    clientSocket=new Socket(host,port);

                    DataOutputStream os=new DataOutputStream(clientSocket.getOutputStream());
                    //DataInputStream is=new DataInputStream(clientSocket.getInputStream());
                    os.writeUTF(msg);

                    os.flush();

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line="";

                    commentArrayList.clear();
                    while ((line = in.readLine()) != null) {
                        noResult=false;
                        serverMsg=line;
                        String[] parts=serverMsg.split(":");
                        try {
                            Comment com = new Comment(parts[0], parts[1], parts[2]);
                            commentArrayList.add(com);
                        }catch(Exception e){
                            noResult=true;
                        }

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
                                if(!commentArrayList.isEmpty()){
                                    loadSearchResult();
                                }



                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_SHORT).show();
                            }
                            // Thread.currentThread().stop();
                        }
                    });

                    os.close();
                    clientSocket.close();

                }catch(IOException e){
                    //Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }finally {


                    //Thread.currentThread().stop();
                }
            }
        });

        clientThread.start();
        if(noResult){
            Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_SHORT).show();
        }

        if(commentArrayList.isEmpty()&&!noResult){
            Toast.makeText(getApplicationContext(), "App is not connected to server", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Please make sure enter correct server ip", Toast.LENGTH_LONG).show();
        }

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        intent_1=getIntent();
                        String serverMsg = "";
                        id=intent_1.getStringExtra("user_id");
                        resID=intent_1.getStringExtra("clickedRestaurantID");
                        rate=(int)rating.getRating();
                        String msg = "storeComment+" + id + "+" + resID + "+" + comment_leave.getText() + "+" + getDate + "+" + rate;
                        BufferedReader in = null;
                        try {
                            clientSocket = new Socket(host, port);

                            DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                            //DataInputStream is=new DataInputStream(clientSocket.getInputStream());
                            os.writeUTF(msg);

                            os.flush();


                            if (in != null) {
                                in.close();
                            }


                            os.close();
                            clientSocket.close();

                        } catch (IOException e) {

                        } finally {


                            //Thread.currentThread().stop();
                        }
                    }
                });
                clientThread.start();
                Toast.makeText(getApplicationContext(), "Comment Submitted. ", Toast.LENGTH_SHORT).show();
                comment_leave.setText("");

            }
        });

    }



    public void loadSearchResult(){
        adapter=new CommentAdapter(getApplicationContext(),R.layout.comment_layout,commentArrayList);
        commentList.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Done Load search result", Toast.LENGTH_SHORT).show();

    }
}
