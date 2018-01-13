package com.example.xtian.cecelectionvotingsystem.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_sqlite;
import com.example.xtian.cecelectionvotingsystem.studentVoteClass.studentVote_class;

import java.sql.Connection;

/**
 * Created by Xtian on 11/18/2015.
 */
public class SetFragment extends android.support.v4.app.Fragment {

    Button btnStartTime,buttonStopTime,buttonFinishTime,buttonNewTime,buttonResume;
    ProgressBar mProgressBar;

    int time_progress; // time progress election
    int time_elapsed;  // time elapsed  election
    int hour_value; // admin set time

    private TextView textViewShowTime; // will show the time
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    //statement and async for database
    private ProgressDialog pDialog;
    boolean authenticated = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;

    String studentID,studentName,studentFlag; // student who login ,, ID,NAME!===============
    DatabaseHandler db = new DatabaseHandler(getActivity());

    public SetFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_set, container, false);

        btnStartTime = (Button)rootView.findViewById(R.id.btnStartTime);
        buttonStopTime = (Button)rootView.findViewById(R.id.btnStopTime);
        buttonFinishTime = (Button)rootView.findViewById(R.id.btnFinishTime);
        buttonResume = (Button)rootView.findViewById(R.id.btnResume);
        buttonNewTime = (Button)rootView.findViewById(R.id.btnNewTime);
        textViewShowTime = (TextView)rootView.findViewById(R.id.tvTimeCount);
        mProgressBar = (ProgressBar)rootView.findViewById(R.id.progressbar);

        Intent intent = getActivity().getIntent();
        studentID = intent.getStringExtra("studentID");
        studentName = intent.getStringExtra("studentName");
        studentFlag = intent.getStringExtra("studentFlag");
        //new flagElectionAsync().execute(); // check flag election


        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentVote();
            }
        });
        return rootView;
    }
    private void studentVote(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                getActivity());
        // Setting Dialog Title
        alertDialog2.setTitle("Confirm choose...");
        // Setting Dialog Message
        alertDialog2.setMessage("Start to vote?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        if (studentFlag.equals("0")){
                            Intent intent = new Intent(getActivity(),studentVote_class.class);
                            intent.putExtra("studentID", studentID);
                            intent.putExtra("studentName",studentName);
                            intent.putExtra("IntentdisplyView", (int) 2);
                            startActivity(intent);

                        }else{
                            AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(getActivity());
                            alertConnectionLost.setTitle("Already voted..");
                            alertConnectionLost.setMessage("This account is already voted!");
                            alertConnectionLost.setCancelable(false);
                            alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertConnectionLost.show();
                        }

                    }
                });
        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        // Showing Alert Dialog
        alertDialog2.show();
    }

    private void checkStudent_flag(){
        String check_ID = studentID;
        item_sqlite ic = db.check_password(check_ID);
        if (ic.getStudentFlag().equals("1")){
            AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(getActivity());
            alertConnectionLost.setTitle("Attention..");
            alertConnectionLost.setMessage("You already vote!");
            alertConnectionLost.setCancelable(false);
            alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertConnectionLost.show();
        }else{
            Toast.makeText(getActivity(), "ok", Toast.LENGTH_LONG).show();
        }
    }






}
