package com.example.xtian.cecelectionvotingsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_candidates;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_complete;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_party;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_position;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Xtian on 11/10/2015.
 */
public class Main_Activity extends Activity {


    private Button BLogin;
    TextView tv;
    EditText TFusername, TFpassword;
    // Progress Dialog
    private ProgressDialog pDialog;

    boolean authenticated = false;
    boolean connectionlost = true;
    String username = "";
    String password = "";
    String studentID;
    String studentName;
    String statement;

    DBConnection dburl = new DBConnection();
    DatabaseHandler db;
    Connection conn;


    String mobile_version;
    String app_version;


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                Main_Activity.this);

        // Setting Dialog Title
        alertDialog2.setTitle("Confirm choose...");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want close this application?");

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        finish();
                        System.exit(0);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mobile_version = String.valueOf(DatabaseHandler.DATABASE_VERSION);
        //connectionStatus();//check connection
        final Animation anim_blink = AnimationUtils.loadAnimation(Main_Activity.this, R.anim.blink);
        BLogin = (Button) this.findViewById(R.id.BLogin);
        TFpassword = (EditText) findViewById(R.id.password);
        TFusername = (EditText) findViewById(R.id.username);
        TFusername.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        TFpassword.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);


        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_blink);
                login_student();
            }
        });
        count_row();
    }

    public void login_student() {
        username = TFusername.getText().toString().replaceAll("'", "''");
        password = TFpassword.getText().toString().replaceAll("'", "''");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            //statement result
            Toast.makeText(getApplicationContext(), "Please fill all input Text ", Toast.LENGTH_SHORT).show();
            TFusername.requestFocus();
            return;
        } else {
            db = new DatabaseHandler(this);
            int authenticated = db.check_student(username,password);;
            if(authenticated==1){
                access_login();
            }else{
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main_Activity.this);
                alertDialog.setTitle("Login Failed");
                alertDialog.setMessage("This account were not found. Please Try Again.");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        TFusername.setText(null);
                        TFpassword.setText(null);
                        TFusername.requestFocus();
                    }
                });
                alertDialog.show();
            }
        }
    }

    //async all data async
    private class allDatatAsync extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {


            statement = "SELECT COUNT(*)AS COUNT FROM students";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);
                    //insert student
                    while (rs.next()) {
                        if (rs.getInt("COUNT") == 0){
                            authenticated = true;
                        }else {
                            statement = "SELECT * FROM students";
                            rs = st.executeQuery(statement);
                            while (rs.next()) {
                                String studentNo = rs.getString("student_No");
                                String studentID =  rs.getString("student_ID");
                                String studentName = rs.getString("student_Name");
                                String studentCourse = rs.getString("student_Course");
                                String studentPass = rs.getString("student_Password");
                                String studentFlag =  rs.getString("flag");
                                db.Add_Student(new item_sqlite(studentNo,studentID,studentName,studentCourse,studentPass,studentFlag));
                            }
                        }
                    }
                    //insert position
                    statement = "SELECT * FROM position";
                    rs = st.executeQuery(statement);
                    while (rs.next()) {
                        String positionID = rs.getString("position_ID");
                        String positionName =  rs.getString("position_Name");
                        db.Add_position(new item_position(positionID, positionName));
                    }
                    //insert candidates
                    statement = "SELECT * FROM candidates";
                    rs = st.executeQuery(statement);
                    while (rs.next()) {
                        String candidate_ID = rs.getString("candidate_ID");
                        String student_ID =  rs.getString("student_ID");
                        String position_ID =  rs.getString("position_ID");
                        String party_ID =  rs.getString("party_ID");
                        String vote_Number =  rs.getString("vote_Number");
                        String candidate_coin =  rs.getString("candidate_coin");
                        db.Add_Candidates(new item_candidates(candidate_ID, student_ID, position_ID, party_ID, vote_Number, candidate_coin));
                    }
                    //insert party
                    statement = "SELECT * FROM party";
                    rs = st.executeQuery(statement);
                    while (rs.next()) {
                        String party_ID = rs.getString("party_ID");
                        String party_Name =  rs.getString("party_Name");
                        db.Add_Party(new item_party(party_ID, party_Name));
                    }
                    String complete = "1";
                    db.Add_Complete(new item_complete(complete));
                } catch (SQLException e) {
                    e.printStackTrace();
                    connectionlost = false;
                }
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main_Activity.this);
            pDialog.setMessage("Syncing  data. Please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pDialog.dismiss();
            //connection status
            if (connectionlost){
                //result
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(Main_Activity.this);
                alertConnectionLost.setTitle("Successful");
                alertConnectionLost.setMessage("Successful!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                alertConnectionLost.show();
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(Main_Activity.this);
                alertConnectionLost.setTitle("Connection..");
                alertConnectionLost.setMessage("No connection in database!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
                alertConnectionLost.show();
            }
        }


    }

    //checking version
    private class checkingAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            statement = "SELECT *  FROM version";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()) {
                        app_version = rs.getString("app_version");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    connectionlost = false;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main_Activity.this);
            pDialog.setMessage("Checking version. Please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //connection status
            if (connectionlost) {
                //result
                if (app_version.equals(mobile_version)){
                    allDatatAsync allDatatAsync = new allDatatAsync();
                    allDatatAsync.execute();
                }else{
                    //lost Connection
                    AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(Main_Activity.this);
                    alertConnectionLost.setTitle("Update new version..");
                    alertConnectionLost.setMessage("This app won't run unless you update!");
                    alertConnectionLost.setCancelable(false);
                    alertConnectionLost.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            System.exit(0);
                        }
                    });
                    alertConnectionLost.show();
                }
            } else {
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(Main_Activity.this);
                alertConnectionLost.setTitle("Connection..");
                alertConnectionLost.setMessage("No connection in database!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
                alertConnectionLost.show();
            }

        }
    }

    //check database
    public void count_row() {
        db = new DatabaseHandler(this);
        int count_total = db.check_database();
        int count_position = db.check_position();
        int count_candidates = db.check_Candidates();
        int count_party = db.check_Party();
        int count_complete = db.check_Complete();
        if (count_total==0){
            AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(Main_Activity.this);
            alertConnectionLost.setTitle("Empty data..");
            alertConnectionLost.setMessage("Please download the data!");
            alertConnectionLost.setCancelable(false);
            alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    checkingAsyncTask checkingAsyncTask = new checkingAsyncTask();
                    checkingAsyncTask.execute();
                }
            });
            alertConnectionLost.show();
        }else{
            if (count_complete==0){
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(Main_Activity.this);
                alertConnectionLost.setTitle("Retry download..");
                alertConnectionLost.setMessage("Download does not complete!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        db.drop_all();
                        checkingAsyncTask checkingAsyncTask = new checkingAsyncTask();
                        checkingAsyncTask.execute();
                    }
                });
                alertConnectionLost.show();
            }else {
                //Toast.makeText(getApplicationContext(),"ok", Toast.LENGTH_LONG).show();
            }
        }
        //Toast.makeText(getApplicationContext(), "student" + count_total +"\n position"+count_position+" candidates"+count_candidates +" party" +count_party +" complete"+count_complete, Toast.LENGTH_LONG).show();
        db.close();
    }

    //access log in
    public void access_login(){
        item_sqlite ic = db.check_password(username);
        if (ic.getStudentPass().equals(password)){
            //Toast.makeText(getApplicationContext(),"ok"+ic.getStudentName(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Main_Activity.this, home.class);
            intent.putExtra("studentID", username);
            intent.putExtra("studentName", ic.getStudentName());
            intent.putExtra("studentFlag", ic.getStudentFlag());
            startActivity(intent);
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main_Activity.this);
            alertDialog.setTitle("Login Failed");
            alertDialog.setMessage("The student ID and password were not found. Please Try Again.");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    TFusername.setText(null);
                    TFpassword.setText(null);
                    TFusername.requestFocus();
                }
            });
            alertDialog.show();
        }
    }
}



