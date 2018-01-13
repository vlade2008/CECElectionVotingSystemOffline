package com.example.xtian.cecelectionvotingsystem.Sqlite_DataDisplay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.adapter.sqliteVoterListAdapter;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Xtian on 11/18/2015.
 */
public class sqlite_voterDisplay extends AppCompatActivity {

    DatabaseHandler db;
    ArrayList<item_sqlite> student_list = new ArrayList<item_sqlite>();

    ListView list;
    ProgressBar pb;
    private ProgressDialog pDialog;
    sqliteVoterListAdapter adapter;
    boolean authenticated = false;
    boolean connectionlost = true;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;

    Button buttonUpload,buttonDeleteStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_voter);
        list = (ListView)findViewById(R.id.listView1);
        //action header bar
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        sqlitestudentAsync sqlitestudentAsync = new sqlitestudentAsync();
        //sqlitestudentAsync.execute();
        displaydate();
        pb = (ProgressBar)findViewById(R.id.br);
        pb.setVisibility(View.INVISIBLE);

        //upload csv file button
        buttonUpload = (Button)findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentAsync studentAsync = new studentAsync();
                studentAsync.execute();

            }
        });



    }
    //ssqlite display data with looping
    public void displaydate(){
        student_list.clear();
        db = new DatabaseHandler(sqlite_voterDisplay.this);
        ArrayList<item_sqlite> student_list_array = db.Get_Student();
        for (int i = 0; i < student_list_array.size(); i++){
            String studentNo = student_list_array.get(i).getStudentNo();
            String studentID =  student_list_array.get(i).getStudentId();
            String studentName = student_list_array.get(i).getStudentName();
            String studentCourse = student_list_array.get(i).getStudentCourse();
            String studentPass = student_list_array.get(i).getStudentPass();
            String studentFlag =  student_list_array.get(i).getStudentFlag();
            student_list.add(new item_sqlite(studentNo, studentID, studentName, studentCourse,studentPass,studentFlag));
        }
        adapter=new sqliteVoterListAdapter(sqlite_voterDisplay.this,R.layout.voterlist,student_list);
        list.setAdapter((ListAdapter) adapter);
    }

    //sqlitestudent data async in listview
    private class sqlitestudentAsync extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            db = new DatabaseHandler(sqlite_voterDisplay.this);
            student_list = db.Get_Student();
            if (student_list.isEmpty())
            {
                db.close();
                authenticated = false;
            }
            else{
                displaydate();
                authenticated = true;

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(sqlite_voterDisplay.this);
            pDialog.setMessage("Loading Student. Please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            if (authenticated){
                db.close();
            }else {

            }

        }


    }

    //student data async in listview
    private class studentAsync extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {


            statement = "SELECT COUNT(*)AS COUNT FROM students";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);

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
            pDialog = new ProgressDialog(sqlite_voterDisplay.this);
            pDialog.setMessage("Loading account. Please wait..");
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
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(sqlite_voterDisplay.this);
                alertConnectionLost.setTitle("sucess..");
                alertConnectionLost.setMessage("sucess!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        displaydate();
                    }
                });
                alertConnectionLost.show();
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(sqlite_voterDisplay.this);
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


}
