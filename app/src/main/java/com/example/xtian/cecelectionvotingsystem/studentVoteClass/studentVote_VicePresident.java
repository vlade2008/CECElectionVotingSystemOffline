package com.example.xtian.cecelectionvotingsystem.studentVoteClass;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.adapter.StudentVoteListAdapater;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.candidateItem;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_views_candidates;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Xtian on 1/24/2016.
 */
public class studentVote_VicePresident extends AppCompatActivity  {

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


    //statement and async for database
    boolean authenticated = false;
    boolean authenticated_2 = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;

    //adapter
    ArrayList<candidateItem> candidateArray;
    StudentVoteListAdapater studentVoteListAdapater;

    String presidentID;
    String VicepresidentID;

    //offline
    DatabaseHandler db;
    ArrayList<item_views_candidates> candidates_list = new ArrayList<item_views_candidates>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_vote);
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
        IntentdisplyViews = intent.getIntExtra("IntentdisplyView", 0);
        position_to_vote = 2;

        count_row();
        TextView textView10 = (TextView)findViewById(R.id.textView10);
        textView10.setText("Vice President");

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_5, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                studentVote();
                return true;
            case R.id.action_check:
                if(VicepresidentID==null)
                {
                    emptyvote();
                    //Toast.makeText(getApplicationContext(), "Select 1 candidate!", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getApplicationContext(),""+presidentID+VicepresidentID, Toast.LENGTH_SHORT).show();
                    studentVote();

                }
                return true;
            case R.id.action_remove:
                list.setItemChecked(hover, false);
                list.setSelection(hover);
                VicepresidentID =null;
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    // vote
    private void studentVote(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                studentVote_VicePresident.this);
        // Setting Dialog Title
        alertDialog2.setTitle("Please Confirm...");
        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Intent intent = new Intent(studentVote_VicePresident.this, studentVote_Secretary.class);
                        intent.putExtra("studentID", studentID);
                        intent.putExtra("studentName", studentName);
                        intent.putExtra("presidentID",presidentID);
                        intent.putExtra("VicepresidentID",VicepresidentID);
                        intent.putExtra("IntentdisplyView", IntentdisplyViews);
                        startActivityForResult(intent, 0);
                        finish();
                        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

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
    // emptyvote
    private void emptyvote(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                studentVote_VicePresident.this);
        // Setting Dialog Title
        alertDialog2.setTitle("Please Confirm...");
        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure not to select a candidate?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        // Write your code here to execute after dialog
                        Intent intent = new Intent(studentVote_VicePresident.this, studentVote_Secretary.class);
                        intent.putExtra("studentID", studentID);
                        intent.putExtra("studentName", studentName);
                        intent.putExtra("presidentID",presidentID);
                        intent.putExtra("VicepresidentID",VicepresidentID);
                        intent.putExtra("IntentdisplyView", IntentdisplyViews);
                        startActivityForResult(intent, 0);
                        finish();
                        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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
        ArrayList<item_views_candidates> candidates_list_array = db.Get_VicePresident();
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
        studentVoteListAdapater = new StudentVoteListAdapater(studentVote_VicePresident.this,R.layout.vote_listcandidates,candidates_list);
        list.setAdapter((ListAdapter) studentVoteListAdapater);

        //list.setDividerHeight(0);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VicepresidentID = ((TextView) view.findViewById(R.id.textcandidateID)).getText()
                        .toString();
                hover = i;
                list.setItemChecked(hover, true);
                list.setSelection(hover);

                //relativeLayout4 = ((RelativeLayout)view.findViewById(R.id.relativeLayout4));
                //relativeLayout4.setBackgroundColor(Color.parseColor("#000000"));

                //Toast.makeText(getApplicationContext(), "" + presidentID, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //check database
    public void count_row() {
        db = new DatabaseHandler(this);
        String position_ID_CHECK = String.valueOf(position_to_vote);
        int count_total = db.check_PositionCandidates(position_ID_CHECK);
        if (count_total==0){
            empty.setText("No Candidate");
        }else{
            displaydate();
        }
        //Toast.makeText(getApplicationContext(), "student" + count_total +"\n position"+count_position+" candidates"+count_candidates +" party" +count_party +" complete"+count_complete, Toast.LENGTH_LONG).show();
        db.close();
    }

}
