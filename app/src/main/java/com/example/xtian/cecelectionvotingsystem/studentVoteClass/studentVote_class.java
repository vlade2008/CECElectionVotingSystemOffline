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
public class studentVote_class extends AppCompatActivity  {

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
    StudentVoteListAdapater studentVoteListAdapater;

    String presidentID;

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
        IntentdisplyViews = intent.getIntExtra("IntentdisplyView", 0);
        position_to_vote = 1;

        count_row();
        TextView textView10 = (TextView)findViewById(R.id.textView10);
        //textView10.setText("Vice President");

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //studentVote();
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
                if(presidentID==null)
                {
                    emptyvote();
                    //Toast.makeText(getApplicationContext(),"Select 1 candidate!" , Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getApplicationContext(),""+presidentID, Toast.LENGTH_SHORT).show();
                    studentVote();

                }
                return true;
            case R.id.action_remove:
                list.setItemChecked(hover, false);
                list.setSelection(hover);
                presidentID =null;
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    // vote
    private void studentVote(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                studentVote_class.this);
        // Setting Dialog Title
        alertDialog2.setTitle("Please Confirm...");
        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Intent intent = new Intent(studentVote_class.this, studentVote_VicePresident.class);
                        intent.putExtra("studentID", studentID);
                        intent.putExtra("studentName", studentName);
                        intent.putExtra("presidentID",presidentID);
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
                studentVote_class.this);
        // Setting Dialog Title
        alertDialog2.setTitle("Please Confirm...");
        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure not to select a candidate?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Intent intent = new Intent(studentVote_class.this, studentVote_VicePresident.class);
                        intent.putExtra("studentID", studentID);
                        intent.putExtra("studentName", studentName);
                        intent.putExtra("presidentID",presidentID);
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
        ArrayList<item_views_candidates> candidates_list_array = db.Get_President();
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
        studentVoteListAdapater = new StudentVoteListAdapater(studentVote_class.this,R.layout.vote_listcandidates,candidates_list);
        list.setAdapter((ListAdapter) studentVoteListAdapater);

        //list.setDividerHeight(0);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                presidentID = ((TextView) view.findViewById(R.id.textcandidateID)).getText()
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


    //offline student
    //==============================  Async class =========================================
    //
    //
    //
//    class  CandidateAsync extends AsyncTask<Void,Integer,Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            statement = "SELECT COUNT(*)AS COUNT FROM candidate_details where position_ID= '"+ position_to_vote +"'";
//            candidateArray = new ArrayList<candidateItem>();
//
//            try {
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
//                try {
//                    conn = DriverManager.getConnection(dburl.getUrl());
//                    Statement st = conn.createStatement();
//                    ResultSet rs = st.executeQuery(statement);
//                    while (rs.next()) {
//                        if (rs.getInt("COUNT") == 0){
//                            authenticated = true;
//                        }else {
//                            statement = "SELECT * FROM candidate_details where position_ID= '"+ position_to_vote +"'";
//                            rs = st.executeQuery(statement);
//                            while (rs.next()) {
//                                // partyId = rs.getString("party_ID");
//                                //partyName = rs.getString("party_Name");
//                                student_ID = rs.getString("student_ID");
//                                student_Name = rs.getString("student_Name");
//                                position_Name = rs.getString("position_Name");
//                                party_Name = rs.getString("party_Name");
//                                candidate_vote = rs.getString("vote_Number");
//                                Blob b = (Blob) rs.getBlob("candidate_img");
//                                byte[] temp = b.getBytes(1, (int) b.length());
//                                //Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length);
//
//                                //for decodebytearray memory
//                                BitmapFactory.Options options = new BitmapFactory.Options();
//                                options.inPurgeable = true;
//                                Bitmap imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
//
//                                candidateArray.add(new candidateItem(student_ID,student_Name,position_Name,party_Name,candidate_vote,imageIcon));
//
//
//                            }
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    connectionlost = false;
//                }
//            } catch (java.lang.InstantiationException e) {
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
//            pDialog = new ProgressDialog(studentVote_class.this);
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
//                TextView textView10 = (TextView)findViewById(R.id.textView10);
//                textView10.setText(position_Name);
//                studentVoteListAdapater = new StudentVoteListAdapater(studentVote_class.this,R.layout.vote_listcandidates,candidateArray);
//                list.setAdapter((ListAdapter) studentVoteListAdapater);
//
//                //list.setDividerHeight(0);
//                list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                             presidentID = ((TextView) view.findViewById(R.id.textcandidateID)).getText()
//                                    .toString();
//                            hover = i;
//                            list.setItemChecked(hover, true);
//                            list.setSelection(hover);
//
//                            //relativeLayout4 = ((RelativeLayout)view.findViewById(R.id.relativeLayout4));
//                            //relativeLayout4.setBackgroundColor(Color.parseColor("#000000"));
//
//                            //Toast.makeText(getApplicationContext(), "" + presidentID, Toast.LENGTH_SHORT).show();
//                        }
//                        });
//
//
//
//                if(authenticated) {empty.setText("No Candidate");} else {}
//                }else{
//                //lost Connection
//                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(studentVote_class.this);
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
