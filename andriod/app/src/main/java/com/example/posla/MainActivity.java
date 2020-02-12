package com.example.posla;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private boolean is_driving = false;
    private String host = "141.223.213.4";
    private int port = 10002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final TextView curf = findViewById(R.id.curFloor);

//        final String host = "141.223.211.186";
//        final int port = 10002;
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// Socket Connection ///////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        // Ref: https://stackoverflow.com/questions/7384678/how-to-create-socket-connection-in-android


        /*
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // curf.setText("Loading...");
                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket(host, port);
                    Log.d("Connection", "Successful!");
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    Log.d("Buffer", "Success");
                    final String st = input.readLine();
                    Log.d("Received", st);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (st.trim().length() != 0)
                                curf.setText(st+"F");
                        }
                    });
                    s.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        */

        //////////////////////////////////////////////////////////////////////////////

        //String floor = "2";  // Receive floor information from server

        //String floorname = floor + "F";
        // Change the floor information in display.
        // Ref: https://stackoverflow.com/questions/7454390/changing-textview-text-on-button-click-android/7454435
        //curf.setText(floorname);

        //create a list of items for the spinner;
        String[] items_f = new String[]{"1F", "2F", "3F"};
        String[] items_1r = new String[]{"107호", "109호", "111호", "113호", "106호","115-1호", "엘리베이터"};
        String[] items_2r = new String[]{"207호", "뭐있는지 모름"};
        String[] items_3r = new String[]{"319호", "Y교수님의 오피스", "정윤우의 비젼랩"};


        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        final ArrayAdapter<String> adapter_f = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_f);
        final ArrayAdapter<String> adapter_1r = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_1r);
        final ArrayAdapter<String> adapter_2r = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_2r);
        final ArrayAdapter<String> adapter_3r = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_3r);

        // Drop down input: get the spinner from the xml.
        // Ref: https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        Spinner dropdown_sf = findViewById(R.id.drSrcFloor);
        Spinner dropdown_sr = findViewById(R.id.drSrcRoom);
        Spinner dropdown_df = findViewById(R.id.drDstFloor);
        Spinner dropdown_dr = findViewById(R.id.drDstRoom);
        Spinner dropdown_floor = findViewById(R.id.floor);

        //set the spinners adapter to the previously created one.
        dropdown_sf.setAdapter(adapter_f);
        dropdown_sr.setAdapter(adapter_1r);
        dropdown_df.setAdapter(adapter_f);
        dropdown_dr.setAdapter(adapter_1r);
        dropdown_floor.setAdapter(adapter_f);


        // OnItemSelected Response
        // Ref: https://android--code.blogspot.com/2015/08/android-spinner-onitemselected.html
        dropdown_sf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Spinner dropdown_sr = findViewById(R.id.drSrcRoom);
                if (parent.getItemAtPosition(pos).equals("1F"))
                    dropdown_sr.setAdapter(adapter_1r);
                else if (parent.getItemAtPosition(pos).equals("2F"))
                    dropdown_sr.setAdapter(adapter_2r);
                else if (parent.getItemAtPosition(pos).equals("3F"))
                    dropdown_sr.setAdapter(adapter_3r);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        dropdown_df.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Spinner dropdown_dr = findViewById(R.id.drDstRoom);
                if (parent.getItemAtPosition(pos).equals("1F"))
                    dropdown_dr.setAdapter(adapter_1r);
                else if (parent.getItemAtPosition(pos).equals("2F"))
                    dropdown_dr.setAdapter(adapter_2r);
                else if (parent.getItemAtPosition(pos).equals("3F"))
                    dropdown_dr.setAdapter(adapter_3r);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        dropdown_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                ImageView img = findViewById(R.id.mapImg);
                if (parent.getItemAtPosition(pos).equals("1F"))
                    img.setImageResource(R.drawable.map_1f);
                else if (parent.getItemAtPosition(pos).equals("2F"))
                    img.setImageResource(R.drawable.map_2f);
                else if (parent.getItemAtPosition(pos).equals("3F"))
                    img.setImageResource(R.drawable.map_2f);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });


        // Button OnClick
        // Ref: https://developer.android.com/reference/android/widget/Button

        final Button button = findViewById(R.id.callBt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Socket s = new Socket(host, port);
                            final OutputStream out = s.getOutputStream();
                            final PrintWriter output = new PrintWriter(out, true);
                            Spinner d = findViewById(R.id.drDstRoom);
                            final String destination = d.getSelectedItem().toString();

                            String sendNum;
                            switch (destination) {
                                case "107호": sendNum = "d:1"; break;
                                case "109호": sendNum = "d:4"; break;
                                case "111호": sendNum = "d:8"; break;
                                case "113호": sendNum = "d:11"; break;
                                case "106호": sendNum = "d:12"; break;
                                case "115-1호": sendNum = "d:15"; break;
                                case "엘리베이터": sendNum = "d:16"; break;
                                default: sendNum = "unexpected";

                            }
                            output.println(sendNum);
                            output.flush();

                            output.close();
                            out.close();
                            s.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

//        final Button controlBt = findViewById(R.id.controlBt);
//        controlBt.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            final Socket s = new Socket(host, port);
//                            final OutputStream out = s.getOutputStream();
//                            final PrintWriter output = new PrintWriter(out, true);
//
//                            output.println("stop");
//                            output.flush();
//                            output.close();
//                            out.close();
//                            s.close();
//                        } catch (IOException e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//            }
//        });
    }

    public void sendSig(View v){

        String msg;
        switch (v.getId()){
            case(R.id.controlBt):
                Button b = findViewById(R.id.controlBt);
                if (b.getText().toString().equals(getString(R.string.stopText))) {
                    msg = "stop";
                    b.setText(getString(R.string.startText));
                }
                else {
                    msg = "start";
                    b.setText(getString(R.string.stopText));
                } break;
//            case (R.id.stopBt):
//                msg = "stop"; break;
//            case (R.id.startBt):
//                msg = "start"; break;
            case (R.id.wBt):
                msg = "c:w"; break;
            case (R.id.aBt):
                msg = "c:a"; break;
            case (R.id.sBt):
                msg = "c:s"; break;
            case (R.id.dBt):
                msg = "c:d"; break;
            case (R.id.modeBt):
                msg = "c:start"; break;
            default:
                msg = "unknown";
        }

        final String sendMsg = msg;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Socket s = new Socket(host, port);
                    final OutputStream out = s.getOutputStream();
                    final PrintWriter output = new PrintWriter(out, true);

                    output.println(sendMsg);
                    output.flush();
                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
