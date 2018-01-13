package com.example.xtian.cecelectionvotingsystem.studentVoteClass;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.adapter.SqliteConfirmAdapater;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.candidateItem;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_views_candidates;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_vote;
import com.example.xtian.cecelectionvotingsystem.home;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Xtian on 1/24/2016.
 */
public class studentVote_confirm extends AppCompatActivity  {

    //widget
    ListView list;
    Button buttonCreate;
    TextView empty,viewparty;
    ProgressBar pb;
    private ProgressDialog pDialog;
    RelativeLayout relativeLayout4;
    int position_to_vote;
    int hover;

    //variables
    String student_ID ;    //listview display candidate ID
    String student_Name ;  //listview display candidate name
    String position_Name; //listview display candidate position_Name
    String party_Name;  //listview display candidate party_Name
    String candidate_vote;  //listview display candidate party_Name



    //intent displayView
    private static Integer IntentdisplyViews;
    String studentID,studentName;
    String PresidentID;


    //statement and async for database
    boolean authenticated = false;
    boolean authenticated_2 = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;

    //adapter
    ArrayList<candidateItem> candidateArray;
    SqliteConfirmAdapater  confirmAdapater;

    String presidentID;
    String VicepresidentID;
    String secretaryID;
    String tresureID;
    String auditorID;
    String proFirstID;
    String proSecondID;
    String firstyearID;
    String secondyearID;
    String thirdyearID;
    String fourthyearID;
    String voteflag;

    //offline
    DatabaseHandler db;
    ArrayList<item_views_candidates> candidates_list = new ArrayList<item_views_candidates>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_confirm);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getSupportActionBar().setTitle("Vote");

        list=(ListView)findViewById(R.id.listviewcandidate);
        empty = (TextView)findViewById(R.id.candidate_empty);
        list.setEmptyView(empty);

        //get data from student   // not fix
        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");
        studentName = intent.getStringExtra("studentName");
        presidentID = intent.getStringExtra("presidentID");
        VicepresidentID = intent.getStringExtra("VicepresidentID");
        secretaryID = intent.getStringExtra("secretaryID");
        tresureID = intent.getStringExtra("tresureID");
        auditorID = intent.getStringExtra("auditorID");
        proFirstID = intent.getStringExtra("proFirstID");
        proSecondID = intent.getStringExtra("proSecondID");
        firstyearID = intent.getStringExtra("firstyearID");
        secondyearID = intent.getStringExtra("secondyearID");
        thirdyearID = intent.getStringExtra("thirdyearID");
        fourthyearID = intent.getStringExtra("fourthyearID");
        IntentdisplyViews = intent.getIntExtra("IntentdisplyView", 0);
        //position_to_vote = 11;

//        CandidateAsync candidateAsync = new CandidateAsync();
//        candidateAsync.execute();
        displaydate();

        Button btnsubmit = (Button)findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitVote();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //cancelstudentVote();
    }
    //submit vote
    private void SubmitVote(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                studentVote_confirm.this);
        // Setting Dialog Title
        alertDialog2.setTitle("Please confirm...");
        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to submit?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                        submitmethod();
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



    //sqlite offline display
    public void displaydate(){
        candidates_list.clear();
        db = new DatabaseHandler(this);
        ArrayList<item_views_candidates> candidates_list_array = db.Get_Confirm(presidentID);
        //president display
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //vice-president
        candidates_list_array = db.Get_Confirm(VicepresidentID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //secretary
        candidates_list_array = db.Get_Confirm(secretaryID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //treasurer
        candidates_list_array = db.Get_Confirm(tresureID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //auditor
        candidates_list_array = db.Get_Confirm(auditorID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //pro-1
        candidates_list_array = db.Get_Confirm(proFirstID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //pro-2
        candidates_list_array = db.Get_Confirm(proSecondID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //1sit year representative
        candidates_list_array = db.Get_Confirm(firstyearID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //secondyearID year representative
        candidates_list_array = db.Get_Confirm(secondyearID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //thirdyearID year representative
        candidates_list_array = db.Get_Confirm(thirdyearID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        //fourthyearID year representative
        candidates_list_array = db.Get_Confirm(fourthyearID);
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        confirmAdapater = new SqliteConfirmAdapater(studentVote_confirm.this,R.layout.vote_listconfirm, candidates_list);
        list.setAdapter((ListAdapter) confirmAdapater);
    }


    public void submitmethod(){
        voteflag = "0";
        db.Add_vote(new item_vote(studentID,presidentID, VicepresidentID, secretaryID, tresureID, auditorID, proFirstID, proSecondID, firstyearID, secondyearID, thirdyearID, fourthyearID,voteflag));
        db.Update_Student(studentID);
        confirmation();
    }
    public void confirmation(){
        AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_confirm.this);
        alertConnectionLost.setTitle("Saved..");
        alertConnectionLost.setMessage("Your vote is still pending!");
        alertConnectionLost.setCancelable(false);
        alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(studentVote_confirm.this,home.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("studentName", studentName);
                intent.putExtra("IntentdisplyView", IntentdisplyViews);
                intent.putExtra("studentFlag","1");
                startActivityForResult(intent, 0);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });
        alertConnectionLost.show();
    }










    //online method
    //==============================  Async class =========================================
    //
    //
    //
//    class  CandidateAsync extends AsyncTask<Void,Integer,Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            statement = "SELECT * FROM candidate_details where student_ID= '"+ presidentID +"'";
//            candidateArray = new ArrayList<candidateItem>();
//
//            try {
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
//                try {
//                    conn = DriverManager.getConnection(dburl.getUrl());
//                    Statement st = conn.createStatement();
//                    ResultSet rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                                student_ID = rs.getString("student_ID");
//                                student_Name = rs.getString("student_Name");
//                                position_Name = rs.getString("position_Name");
//                                party_Name = rs.getString("party_Name");
//                                candidate_vote = rs.getString("vote_Number");
//                                Blob b = (Blob) rs.getBlob("candidate_img");
//                                byte[] temp = b.getBytes(1, (int) b.length());
//                                BitmapFactory.Options options = new BitmapFactory.Options();
//                                options.inPurgeable = true;
//                                Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                                candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ VicepresidentID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ secretaryID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ tresureID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ auditorID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ proFirstID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ proSecondID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ firstyearID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ secondyearID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ thirdyearID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//                    statement = "SELECT * FROM candidate_details where student_ID= '"+ fourthyearID +"'";
//                    rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        student_ID = rs.getString("student_ID");
//                        student_Name = rs.getString("student_Name");
//                        position_Name = rs.getString("position_Name");
//                        party_Name = rs.getString("party_Name");
//                        candidate_vote = rs.getString("vote_Number");
//                        Blob b = (Blob) rs.getBlob("candidate_img");
//                        byte[] temp = b.getBytes(1, (int) b.length());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPurgeable = true;
//                        Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                        candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    connectionlost = false;
//                }
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(studentVote_confirm.this);
//            pDialog.setMessage("Loading Candidate. Please wait..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            //connection status
//            if (connectionlost){
//                //result
//                pDialog.dismiss();
//                //confirmAdapater = new SqliteConfirmAdapater(studentVote_confirm.this,R.layout.vote_listconfirm,candidateArray);
//                list.setAdapter((ListAdapter) confirmAdapater);
//
//                if(authenticated) {empty.setText("No Candidate");} else {}
//                }else{
//                //lost Connection
//                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_confirm.this);
//                alertConnectionLost.setTitle("Connection..");
//                alertConnectionLost.setMessage("No connection in database!");
//                alertConnectionLost.setCancelable(false);
//                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        System.exit(0);
//                    }
//                });
//                alertConnectionLost.show();
//            }
//        }
//    }
//
//    class  SubmitVoteAsync extends AsyncTask<Void,Integer,Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
//                try {
//                    conn = DriverManager.getConnection(dburl.getUrl());
//                    Statement st = conn.createStatement();
//                    //president
//                    PreparedStatement stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,presidentID);
//                    stInsert.executeUpdate();
//                    //vice president
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,VicepresidentID);
//                    stInsert.executeUpdate();
//                    //Secretary
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,secretaryID);
//                    stInsert.executeUpdate();
//                    //Treasure
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,tresureID);
//                    stInsert.executeUpdate();
//                    //Auditor
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,auditorID);
//                    stInsert.executeUpdate();
//                    //Pro-1
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,proFirstID);
//                    stInsert.executeUpdate();
//                    //Pro-2
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,proSecondID);
//                    stInsert.executeUpdate();
//                    //1st year Representative
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,firstyearID);
//                    stInsert.executeUpdate();
//                    //2nd year Representative
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,secondyearID);
//                    stInsert.executeUpdate();
//                    //3rd year Representative
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,thirdyearID);
//                    stInsert.executeUpdate();
//                    //4th year Representative
//                    stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,fourthyearID);
//                    stInsert.executeUpdate();
//
//                    //student flag
//                    stInsert = conn.prepareStatement("UPDATE students SET flag=? WHERE student_ID=? ");
//                    stInsert.setInt(1, 1);
//                    stInsert.setString(2,studentID);
//                    stInsert.executeUpdate();
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    connectionlost = false;
//                }
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            pDialog = new ProgressDialog(studentVote_confirm.this);
////            pDialog.setMessage("Submiting vote. Please wait..");
////            pDialog.setIndeterminate(false);
////            pDialog.setCancelable(false);
////            pDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            //connection status
//            if (connectionlost){
//                //result
//                pDialog.dismiss();
//                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_confirm.this);
//                alertConnectionLost.setTitle("Successful..");
//                alertConnectionLost.setMessage("Your vote was counted!");
//                alertConnectionLost.setCancelable(false);
//                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        Intent intent = new Intent(studentVote_confirm.this,home.class);
//                        intent.putExtra("studentID", studentID);
//                        intent.putExtra("studentName", studentName);
//                        intent.putExtra("IntentdisplyView", IntentdisplyViews);
//                        startActivityForResult(intent, 0);
//                        finish();
//                        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//                    }
//                });
//                alertConnectionLost.show();
//
//            }else{
//                //lost Connection
//                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_confirm.this);
//                alertConnectionLost.setTitle("Connection..");
//                alertConnectionLost.setMessage("No connection in database!");
//                alertConnectionLost.setCancelable(false);
//                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        System.exit(0);
//                    }
//                });
//                alertConnectionLost.show();
//            }
//        }
//    }
//
//    class  CheckAsync extends AsyncTask<Void,Integer,Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            String check_flag;
//            statement = "SELECT * FROM students where student_ID= '"+ studentID +"'";
//            candidateArray = new ArrayList<candidateItem>();
//
//            try {
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
//                try {
//                    conn = DriverManager.getConnection(dburl.getUrl());
//                    Statement st = conn.createStatement();
//                    ResultSet rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        check_flag = rs.getString("flag");
//                        if(check_flag.equals("1")){
//                            authenticated = true;
//                        }else{
//                            authenticated=false;
//                        }
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    connectionlost = false;
//                }
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(studentVote_confirm.this);
//            pDialog.setMessage("Submiting vote. Please wait..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            //connection status
//            if (connectionlost){
//                //result
//                if(authenticated) {
//                    pDialog.dismiss();
//                    AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_confirm.this);
//                    alertConnectionLost.setTitle("Attention..");
//                    alertConnectionLost.setMessage("You already vote!\n Your vote will not be counted");
//                    alertConnectionLost.setCancelable(false);
//                    alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                            Intent intent = new Intent(studentVote_confirm.this,home.class);
//                            intent.putExtra("studentID", studentID);
//                            intent.putExtra("studentName", studentName);
//                            intent.putExtra("IntentdisplyView", IntentdisplyViews);
//                            startActivityForResult(intent, 0);
//                            finish();
//                            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//                        }
//                    });
//                    alertConnectionLost.show();
//                } else {
//                    SubmitVoteAsync submitVoteAsync = new SubmitVoteAsync();
//                    submitVoteAsync.execute();
//                }
//            }else{
//                //lost Connection
//                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_confirm.this);
//                alertConnectionLost.setTitle("Connection..");
//                alertConnectionLost.setMessage("No connection in database!");
//                alertConnectionLost.setCancelable(false);
//                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        System.exit(0);
//                    }
//                });
//                alertConnectionLost.show();
//            }
//        }
//    }
}
