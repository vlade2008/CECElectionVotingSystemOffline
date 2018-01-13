package com.example.xtian.cecelectionvotingsystem.VoterFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.adapter.VoterListAdapter;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Xtian on 11/18/2015.
 */
public class VoterFragment_notVoter extends android.support.v4.app.Fragment {



    String name ;
    String studentId ;
    String course ;
    String studentpassword ;
    Integer count_voters;

    ListView list;
    Button buttonUpload,buttonDeleteStudent;
    TextView empty;


    ProgressBar pb;
    private ProgressDialog pDialog;


    ArrayList<item> studentArray;
    VoterListAdapter adapter;

    //statement and async for database
    boolean authenticated = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;



    public VoterFragment_notVoter(){}



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notvoter, container, false);

        final Animation anim_blink = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        pb = (ProgressBar)rootView.findViewById(R.id.br);

        //list view
        //adapter=new VoterListAdapter(this.getActivity(),R.layout.voterlist,studentArray);
        list=(ListView)rootView.findViewById(R.id.listView1);
         empty = (TextView)rootView.findViewById(R.id.empty);
        list.setEmptyView(empty);
        //list.setAdapter((ListAdapter) adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String titre = ((TextView) view.findViewById(R.id.textName)).getText()
                        .toString();
                Toast.makeText(getActivity(), "" + titre, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteStudent = (Button)rootView.findViewById(R.id.buttonDeleteStudent);



        //async student
        new flagElectionAsync().execute();
        return rootView;
    }

    //==============================  Async class =========================================

    //flag election ,, if the election start,or finish
    //==================================================
    class flagElectionAsync extends AsyncTask<Void,Integer,Void>{

        boolean flag_NotStart = false;
        boolean flag_Start = false;
        boolean flag_finish = false;

        @Override
        protected Void doInBackground(Void... params) {
            statement = "SELECT * FROM flag";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);

                    while (rs.next()) {
                        //set time for the election
                        int flag_election = rs.getInt("set_election");
                        if (flag_election==0){
                            flag_NotStart = true;
                        }else if (flag_election==1){
                            flag_Start = true;
                        }else if (flag_election==2){
                            flag_finish = true;
                        }

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
            pb.setVisibility(getView().VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //connection status
            if (connectionlost){
                if (getActivity()!=null) {
                    if (flag_NotStart) {
                        pb.setVisibility(getView().INVISIBLE);
                        buttonDeleteStudent.setText("Election not yet started.");
                        empty.setText("Election not yet started.");
                    } else if (flag_Start) {
                        pb.setVisibility(getView().INVISIBLE);
                        buttonDeleteStudent.setText("Election not yet finished.");
                        empty.setText("Election not yet finished.");
                    } else if (flag_finish) {
                        new studentAsync().execute();
                    }
                }else{}
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(getActivity());
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
    //student data async in listview
    private class studentAsync extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {


            statement = "SELECT COUNT(*)AS COUNT FROM students where flag='0'";
            studentArray = new ArrayList<item>();

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);

                    while (rs.next()) {
                        count_voters = rs.getInt("COUNT");
                        if (rs.getInt("COUNT") == 0){
                            authenticated = true;
                        }else {
                            statement = "SELECT * FROM students where flag='0'";
                            rs = st.executeQuery(statement);
                            while (rs.next()) {
                                studentId = rs.getString("student_ID");
                                name = rs.getString("student_Name");
                                course = rs.getString("student_Course");
                                studentpassword = rs.getString("student_Password");
                                studentArray.add(new item(studentId, name, course, studentpassword));

                            }
                        }
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
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pb.setVisibility(getView().INVISIBLE);
            //connection status
            if (connectionlost){
                //result
                if (getActivity()!=null) {
                    adapter=new VoterListAdapter(getActivity(),R.layout.voterlist,studentArray);
                    list.setAdapter((ListAdapter) adapter);
                    buttonDeleteStudent.setText(count_voters+" not vote!");
                    if (authenticated) {
                        //true student
                        empty.setText("No student vote");
                    } else {
                        // false student
                    }
                }else{}
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(getActivity());
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
