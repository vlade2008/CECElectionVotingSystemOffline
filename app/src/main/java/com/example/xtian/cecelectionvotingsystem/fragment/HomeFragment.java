package com.example.xtian.cecelectionvotingsystem.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Xtian on 11/16/2015.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    String studentID,studentName; // student who login ,, ID,NAME!===============

    Bitmap imageIcon;
    ImageView imageView2;

    private ProgressDialog pDialog;
    //statement and async for database
    boolean authenticated = false;
    boolean authenticated_2 = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;
    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        Intent intent = getActivity().getIntent();
        studentID = intent.getStringExtra("studentID");
        studentName = intent.getStringExtra("studentName");
        //Toast.makeText(getActivity(), "ID " + studentID, Toast.LENGTH_LONG).show();
        TextView textView8 = (TextView)rootView.findViewById(R.id.textView8);
        imageView2 = (ImageView)rootView.findViewById(R.id.imageView2);

        textView8.setText(studentName);

        //new imageStudent().execute();
        return rootView;
    }


    class imageStudent extends AsyncTask<Void,Integer,Void> {



        @Override
        protected Void doInBackground(Void... params) {
            statement = "SELECT COUNT(*)AS COUNT FROM candidates where student_ID= '" + studentID + "'";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);

                    while (rs.next()) {
                        if (rs.getInt("COUNT") == 0){
                            authenticated = false;
                        }
                        else{
                            statement = "SELECT * FROM candidates where student_ID= '" + studentID + "'";
                            rs = st.executeQuery(statement);
                            while (rs.next()){
                                Blob b = (Blob) rs.getBlob("candidate_img");
                                byte[] temp = b.getBytes(1, (int) b.length());
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inPurgeable = true;
                                imageIcon = BitmapFactory.decodeByteArray(temp, 0, temp.length, options);
                                authenticated = true;
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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading student. Please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //connection status
            if (connectionlost){

                if (authenticated){
                    imageView2.setImageBitmap(imageIcon);
                    pDialog.dismiss();
                }else{
                    pDialog.dismiss();
                }
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
